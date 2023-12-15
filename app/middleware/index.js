const authorization = require("./authorization");
const emailCheck = require("./emailCheck");
const iam = require("./iam");
const uploadFileGCS = require('./uploadFileGCS')
const classification = require('./classification')

module.exports = {
  authorization,
  emailCheck,
  iam,
  uploadFileGCS,
  classification,
};
