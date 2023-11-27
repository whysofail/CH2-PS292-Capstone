const express = require("express");
const router = express.Router();

const { authController } = require("../controllers");

const middleware = require("../middleware");

router.post("/login", authController.login);
router.post("/register", authController.register);
router.get("/who", middleware.authorize, authController.whoAmI);

module.exports = router;
