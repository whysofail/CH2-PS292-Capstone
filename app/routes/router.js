const express = require("express");
const router = express.Router();

const consultationRoute = require('./consultationRoute')
const {
  authController,
  lawyerController,
  databaseController,
  chatController,
  imageController,
  ocrController,
  consultationController,
} = require("../controllers");

const middleware = require("../middleware");

router.get(
  "/chat",
  middleware.iam.getIdTokenFromMetadataServer,
  chatController.getServerStatus
);

router.get("/databasestatus", databaseController.checkDatabaseConn);

router.post("/login", authController.login);
router.get("/logout", authController.logout);
router.post(
  "/register",
  middleware.emailCheck.emailExist,
  authController.register
);
router.get("/who", middleware.authorization.authorize, authController.whoAmI);

router.get("/lawyer", lawyerController.getLawyer);
router.get("/lawyer/search", lawyerController.searchLawyerByTag);

router.post("/ocr", ocrController.upload.single("file"), ocrController.uploadImage, ocrController.ocrImage);

router.post(
  "/chat",
  middleware.authorization.authorize,
  middleware.iam.getIdTokenFromMetadataServer,
  chatController.getChat
);

router.post(
  "/testimage",
  middleware.uploadFileGCS.uploadImage,
  imageController.catchImageURI
);

router.use('/consultation', consultationRoute)

module.exports = router;
