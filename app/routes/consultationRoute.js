const express = require("express");
const router = express.Router();

const { consultationController } = require("../controllers");

const middleware = require("../middleware");

router.get(
  "/",
  middleware.authorization.authorize,
  consultationController.getAllConsultation
);
router.get(
  "/user",
  middleware.authorization.authorize,
  consultationController.getAllConsultationByUserId
);
router.get(
  "/lawyer",
  middleware.authorization.authorize,
  consultationController.getAllConsultationByLawyerId
);
router.post(
  "/",
  middleware.authorization.authorize,
  middleware.multer.processFileMiddleware,
  middleware.multer.uploadImage,
  middleware.classification.getImageClassification,
  middleware.ocr.ocrImage,
  consultationController.createConsultation
);
router.get(
  "/:id",
  middleware.authorization.authorize,
  consultationController.getConsultationById
);
router.put(
  "/:id",
  middleware.authorization.authorize,
  middleware.multer.processFileMiddleware,
  middleware.multer.uploadImage,
  middleware.classification.getImageClassification,
  middleware.ocr.ocrImage,
  consultationController.updateConsultation
);


module.exports = router;
