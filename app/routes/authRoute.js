const express = require("express");
const router = express.Router();

const { authController } = require("../controllers");
const middleware = require("../middleware");


router.post("/login", authController.login);
router.get("/logout", authController.logout);
router.post(
  "/register",
  middleware.emailCheck.emailExist,
  authController.register
);
router.get("/who", middleware.authorization.authorize, authController.whoAmI);

module.exports = router