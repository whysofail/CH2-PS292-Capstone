const { Storage } = require('@google-cloud/storage');
const { ImageAnnotatorClient } = require('@google-cloud/vision');

// Inisialisasi Google Cloud Storage dan Google Cloud Vision client
const storage = new Storage();
const vision = new ImageAnnotatorClient();

// Nama bucket Google Cloud Storage
const bucketName = 'nama_bucket';

// Nama folder di bucket untuk menyimpan gambar
const folderName = 'gambar_ocr/';

// Fungsi untuk melakukan OCR pada file
const ocrImage = async (req, res) => {
    try {
      // Pastikan path file yang telah disimpan di req
      const filePath = req.uploadedFilePath;
  
      // Baca file dari path
      const fileContent = fs.readFileSync(filePath);
  
      // Konfigurasi Google Cloud Vision
      const client = new vision.ImageAnnotatorClient();
      const request = {
        image: {
          content: fileContent,
        },
      };
  
      // Lakukan OCR pada file
      const [result] = await client.textDetection(request);
      const detections = result.textAnnotations;
  
      // Kirim respons OCR
      res.status(200).json({ result: detections });
    } catch (error) {
      console.error('Error during OCR:', error);
      res.status(500).json({ message: 'Internal Server Error' });
    }
  };

// Nama fungsi untuk mengunggah gambar ke Google Cloud Storage
const uploadImage = async (req, res, next) => {
    try {
      const file = req.file;
  
      if (!file) {
        return res.status(400).json({ error: "Tidak ada file yang diunggah" });
      }
  
      // Menyimpan path file yang diunggah ke req untuk digunakan di middleware selanjutnya
      req.uploadedFilePath = file.path;
  
      // Lanjut ke middleware selanjutnya
      next();
    } catch (error) {
      console.error('Error during image upload:', error);
      return res.status(500).json({ message: 'Internal Server Error' });
    }
  };
  

module.exports = {
  uploadImage,
  ocrImage,
};
