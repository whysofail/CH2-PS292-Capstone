const express = require("express");
const router = express.Router();
const {
    lawyerController,
  } = require("../controllers");

const middleware = require("../middleware");


router.get("/", lawyerController.getLawyer);
router.get("/search", lawyerController.searchLawyerByTag);

module.exports = router