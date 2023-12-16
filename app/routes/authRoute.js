const express = require("express");
const router = express.Router();

const { authController } = require("../controllers");
const middleware = require("../middleware");


router.post("/login", authController.login);
router.get("/logout", authController.logout);
router.post(
  "/register",
  middleware.multer.processFileMiddleware,
  middleware.emailCheck.emailExist,
  middleware.multer.uploadImage,
  authController.register,
);
router.put(
  "/update",
  middleware.authorization.authorize,
  middleware.multer.processFileMiddleware,
  middleware.multer.uploadImage,
  authController.updateUser
);


router.get("/who", middleware.authorization.authorize, authController.whoAmI);

module.exports = router