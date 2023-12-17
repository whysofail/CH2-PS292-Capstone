const authorization = require("./authorization");
const emailCheck = require("./emailCheck");
const iam = require("./iam");
const multer = require('./multer')
const classification = require('./classification')

module.exports = {
  authorization,
  emailCheck,
  iam,
  multer,
  classification,
};
