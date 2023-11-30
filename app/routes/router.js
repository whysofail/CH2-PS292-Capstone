const express = require("express");
const router = express.Router();

const { authController } = require("../controllers");
<<<<<<< HEAD
const { lawyerController } = require('../controllers/lawyerController');
=======
>>>>>>> 53c27355258caf3fd251af41b08b3ebc14d69f80

const middleware = require("../middleware");

router.post("/login", authController.login);
router.post("/register", middleware.emailExist, authController.register);
router.get("/who", middleware.authorize, authController.whoAmI);
<<<<<<< HEAD
router.get('/lawyers', lawyerController.getLawyers);
=======
>>>>>>> 53c27355258caf3fd251af41b08b3ebc14d69f80

module.exports = router;
