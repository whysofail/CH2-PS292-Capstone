const express = require("express");
const router = express.Router();

const {
  consultationController,
} = require("../controllers");

const middleware = require("../middleware");


router.get(
    "/",
    middleware.authorization.authorize,
    consultationController.getAllConsultation
  );
  router.get(
    "/:id",
    middleware.authorization.authorize,
    consultationController.getConsultationById
  );
  router.post(
    "/:id",
    middleware.authorization.authorize,
    middleware.uploadFileGCS.uploadImage,
    consultationController.createConsultation
  );
  router.put(
    "/:id",
    middleware.authorization.authorize,
    middleware.uploadFileGCS.uploadImage,
    consultationController.updateConsultation
  );

  module.exports = router