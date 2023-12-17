const express = require("express");
const router = express.Router();

const middleware = require("../middleware");

const { databaseController, chatController } = require("../controllers");

router.get("/database", databaseController.checkDatabaseConn);

router.get(
  "/chat",
  middleware.iam.getIdTokenFromMetadataServer,
  chatController.getServerStatus
);

module.exports = router
