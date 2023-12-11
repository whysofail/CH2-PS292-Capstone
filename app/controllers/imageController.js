// const express = require("express");
const processFile = require("../middleware/uploadFile");
const { format } = require("util");
const { Storage } = require("@google-cloud/storage");
const storage = new Storage();
const bucket = storage.bucket("lawmate_user_bucket");
// Fungsi untuk mengunggah gambar ke Google Cloud Storage

const catchImageURI = async (req, res) => {
  const imageURI = req.imagePublic_URI;
  return res.status(200).json({ msg: imageURI });
};

module.exports = {
  catchImageURI
};
