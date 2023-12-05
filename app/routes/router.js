const express = require("express");
const router = express.Router();

const { authController, lawyerController, databaseController, ocrController } = require("../controllers");

const middleware = require("../middleware");

router.post("/login", authController.login);
router.post("/register", middleware.emailExist, authController.register);
router.get("/logout", authController.logout);
router.get("/who", middleware.authorize, authController.whoAmI);
router.get("/lawyer", lawyerController.getLawyer);
router.get("/search-lawyer-by-tag", lawyerController.searchLawyerByTag);

router.get("/databasestatus", databaseController.checkDatabaseConn)

router.post("/ocr", middleware.authorize, ocrController.uploadImage, ocrController.ocrImage);

module.exports = router;
