const authController = require("./authController");
const lawyerController = require("./lawyerController");
const databaseController = require("./databaseController");
const chatController = require("./chatController");
const imageController = require('./imageController')
const consultationController = require('./consultationController')

module.exports = {
  authController,
  databaseController,
  lawyerController,
  chatController,
  imageController,
  consultationController,
};
