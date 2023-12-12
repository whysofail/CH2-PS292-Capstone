const express = require("express");
const router = express.Router();

const {
  authController,
  lawyerController,
  databaseController,
  chatController,
} = require("../controllers");

const middleware = require("../middleware");

router.get(
  "/bots",
  middleware.getIdTokenFromMetadataServer,
  chatController.getServerStatus
);
router.get("/databasestatus", databaseController.checkDatabaseConn);

router.post("/login", authController.login);
router.get("/logout", authController.logout);
router.post("/register", middleware.emailExist, authController.register);
router.get("/logout", authController.logout);
router.get("/who", middleware.authorize, authController.whoAmI);

router.get("/lawyer", lawyerController.getLawyer);
router.get("/lawyer/search", lawyerController.searchLawyerByTag);


router.post(
  "/chat",
  middleware.authorize,
  middleware.getIdTokenFromMetadataServer,
  chatController.getChat
);

module.exports = router;
