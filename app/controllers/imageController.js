// const express = require("express");
const processFile = require("../middleware/multer");
const { format } = require("util");
const { Storage } = require("@google-cloud/storage");
const storage = new Storage();
const bucket = storage.bucket("lawmate_user_bucket");
const { User, Consultation } = require("../../models");
// Fungsi untuk mengunggah gambar ke Google Cloud Storage

const catchImageURI = async (req, res) => {
  const imageURI = req.imagePublic_URI;
  const { user_id, consultation_id } = req.body;

  try {
    if (user_id) {
      const user = await User.findByPk(user_id);
      if (!user) {
        return res.status(404).json({ msg: "User not found" });
      }

      user.profile_picture = imageURI;
      await user.save();

      return res.status(200).json({ msg: "Profile picture updated successfully" });
    } else if (consultation_id) {
      const consultation = await Consultation.findByPk(consultation_id);
      if (!consultation) {
        return res.status(404).json({ msg: "Consultation not found" });
      }

      consultation.picture_URI = imageURI;
      await consultation.save();

      return res.status(200).json({ msg: "Consultation picture updated successfully" });
    } else {
      return res.status(400).json({ msg: "Please provide either user_id or consultation_id" });
    }
  } catch (err) {
    console.error("Error updating picture:", err);
    return res.status(500).json({
      error: {
        name: err.name,
        msg: "An error occurred during picture update",
      },
    });
  }
};

module.exports = {
  catchImageURI
};