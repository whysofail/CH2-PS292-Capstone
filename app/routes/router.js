const express = require("express");
const router = express.Router();

const { authController } = require("../controllers");
const { lawyerController } = require('../controllers');


const middleware = require("../middleware");

router.post("/login", authController.login);
router.post("/register", middleware.emailExist, authController.register);
router.get("/who", middleware.authorize, authController.whoAmI);
router.get('/user/:role_id', lawyerController.getUsersByRole);

module.exports = router;
