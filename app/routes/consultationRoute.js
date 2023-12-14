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
router.post(
  "/",
  middleware.authorization.authorize,
  middleware.uploadFileGCS.uploadImage,
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
  middleware.uploadFileGCS.uploadImage,
  consultationController.updateConsultation
);

module.exports = router;
