const express = require("express");
const router = express.Router();

const { authController } = require("../controllers");
const { lawyerController } = require('../controllers/lawyerController');

const middleware = require("../middleware");

router.post("/login", authController.login);
router.post("/register", middleware.emailExist, authController.register);
router.get("/who", middleware.authorize, authController.whoAmI);
router.get('/lawyers', lawyerController.getLawyers);

module.exports = router;
