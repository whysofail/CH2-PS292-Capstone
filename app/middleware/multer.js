const util = require("util");
const Multer = require("multer");
const { format } = require("util");
const { v4: uuidv4 } = require("uuid");
const { Storage } = require("@google-cloud/storage");

const storage = new Storage();
const bucket = storage.bucket("lawmate_user_bucket");

const maxSize = 2 * 1024 * 1024;

let processFile = Multer({
  storage: Multer.memoryStorage(),
  limits: { fileSize: maxSize },
}).single("file");

let processFileMiddleware = util.promisify(processFile);

const uploadImage = async (req, res, next) => {
  try {
    if (req.file === undefined) {
      console.log('No file attached')
      req.imagePublic_URI = null;
      next();
    } else {
      console.log('file attached : ' + req.file.originalname)
      const fileName = generateRandomFileName(req.file.originalname);
      const blob = bucket.file(fileName);
      const blobStream = blob.createWriteStream({
        resumable: false,
      });

      blobStream.on("error", (err) => {
        res.status(500).send({ message: err.message });
      });

      blobStream.on("finish", async () => {
        const publicUrl = format(
          `https://storage.googleapis.com/${bucket.name}/${blob.name}`
        );
        req.gs_URI = `gs://${bucket.name}/${blob.name}`
        req.imagePublic_URI = encodeURI(publicUrl);
        console.log(`Image uploaded : ${publicUrl}`)
        next();
      });

      blobStream.end(req.file.buffer);
    }
  } catch (error) {
    console.error("Error during image upload:", error);
    return res.status(500).json({ message: "Internal Server Error" });
  }
};

const generateRandomFileName = (originalName) => {
  const timestamp = new Date().toISOString().replace(/[-:]/g, ""); // Remove dashes and colons from the timestamp
  const uniqueId = uuidv4().replace(/-/g, ""); // Remove dashes from the UUID
  const fileExtension = originalName.split(".").pop(); // Get the file extension from the original name
  const randomFileName = `${timestamp}_${uniqueId}.${fileExtension}`;
  return randomFileName;
};

module.exports = { processFileMiddleware, uploadImage };
