const express = require("express");
const router = express.Router();

const { authController, userController, databaseController } = require("../controllers");

const middleware = require("../middleware");

router.post("/login", authController.login);
router.post("/register", middleware.emailExist, authController.register);
router.get("/who", middleware.authorize, authController.whoAmI);
router.post('/user', userController.getUsersByRole);

router.get('/databasestatus', databaseController.checkDatabaseConn)

module.exports = router;
