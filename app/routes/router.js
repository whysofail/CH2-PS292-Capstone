const express = require("express");
const router = express.Router();

const { authController, lawyerController, databaseController } = require("../controllers");

const middleware = require("../middleware");

router.post("/login", authController.login);
router.post("/register", middleware.emailExist, authController.register);
router.get("/who", middleware.authorize, authController.whoAmI);
router.get('/lawyer', lawyerController.getLawyer);
router.get('/lawyer/search', lawyerController.searchLawyerByTag);

router.get('/databasestatus', databaseController.checkDatabaseConn)

module.exports = router;
