const express = require("express");
const router = express.Router();

const consultationRoute = require("./consultationRoute");
const authRoute = require("./authRoute");
const lawyerRoute = require('./lawyerRoute')
const statusRoute = require('./statusRoute')
const {
  chatController,
} = require("../controllers");

const middleware = require("../middleware");

router.post(
  "/chat",
  middleware.authorization.authorize,
  middleware.iam.getIdTokenFromMetadataServer,
  chatController.getChat
);

router.use("/auth", authRoute);
router.use('/lawyer', lawyerRoute)
router.use("/consultation", consultationRoute);
router.use('/status', statusRoute)

module.exports = router;
