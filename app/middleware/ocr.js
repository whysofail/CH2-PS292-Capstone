const { ImageAnnotatorClient } = require('@google-cloud/vision');
const vision = new ImageAnnotatorClient();


const ocrImage = async (req, res, next) => {
    try {
      // Pastikan buffer file yang telah disimpan di req
      const imageType = req.imageType
      const gs_URI = req.gs_URI
      console.log(imageType)
      if(imageType !== 'chat' || imageType == undefined){
        console.log('Image is not a chat or undefined')
        next()
      }
      // Lakukan OCR pada file
      const [result] = await vision.textDetection(gs_URI);
      const detections = result.textAnnotations;
  
      // Kirim respons OCR
      req.extracted_text = detections
      next()
    } catch (error) {
      console.error('Error during OCR:', error);
      res.status(500).json({ message: 'Internal Server Error' });
    }
  };
  
  module.exports = {
    ocrImage,
  };