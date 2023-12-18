const { User, Consultation } = require("../../models");

const getAllConsultation = async (req, res) => {
  try {
    const consultation = await Consultation.findAll();
    return res.status(200).json({ msg: consultation });
  } catch (error) {
    console.error(error);
    return res.status(500).json({ msg: "Internal server error" });
  }
};

const getAllConsultationByUserId = async (req, res) => {
  const { user } = req;
  try {
    const consultation = await Consultation.findAll({
      where: {
        user_id: user.id,
      },
    });
    if (!consultation) {
      return res.status(400).json({ msg: "No consultation found" });
    }
    return res.status(200).json({ msg: consultation });
  } catch (error) {
    return res.status(500).json({ msg: "Internal server error." });
  }
};

const getConsultationById = async (req, res) => {
  try {
    const { id } = req.query;
    const user = req.user;

    const consultation = await Consultation.findOne(id, {
      where: {
        id,
        user_id: user.id,
      },
    });

    if (!consultation) {
      return res.status(400).json({
        msg: "Consultation not found or you dont have permission to see this.",
      });
    }
    res.status(200).json({ msg: consultation });
  } catch (error) {
    console.error("Error fetching consultation by ID:", error);
    res.status(500).json({ msg: "Internal Server Error" });
  }
};

const createConsultation = async (req, res) => {
  const { user, imagePublic_URI, extracted_text } = req;
  const { lawyer_id } = req.query;
  const { title, description } = req.body;
  const picture_URI = imagePublic_URI || null;
  let extractArray = [];
  if (picture_URI !== null) {
    extractArray = extracted_text.map((item) => item.description);
  }
  if (!lawyer_id) {
    return res.status(400).json({ msg: "No lawyer id" });
  }
  const lawyer = await User.findByPk(lawyer_id);
  if (!lawyer) {
    return res
      .status(400)
      .json({ msg: `No lawyer found with id : ${lawyer_id}` });
  }
  try {
    const consultation = await Consultation.create({
      title,
      description,
      picture_URI,
      user_id: user.id,
      lawyer_id,
    });

    return res.status(200).json({ msg: consultation, extractArray });
  } catch (error) {
    console.error(error);
    return res
      .status(500)
      .json({ error: "Internal server error", details: error.message });
  }
};

const updateConsultation = async (req, res) => {
  try {
    const { user, imagePublic_URI, extracted_text } = req;
    const picture_URI = imagePublic_URI || null;
    let extractArray = [];
    if (picture_URI !== null) {
      extractArray = extracted_text.map((item) => item.description);
    }
    const id = req.params.id;

    let consultation = await Consultation.findByPk(id, {
      where: {
        user_id: user.id,
      },
    });

    if (!consultation) {
      return res.status(400).json({
        msg: "Consultation not found or you dont have permission to see this.",
      });
    }

    const updatedData = req.body;

    consultation = Object.assign(consultation, updatedData);
    await Consultation.update(consultation, {
      where: {
        id,
      },
    });

    res.status(200).json({ msg: "Update success", data: consultation });
  } catch (error) {
    console.error("Error updating consultation:", error);
    res.status(500).json({ msg: "Internal Server Error" });
  }
};

module.exports = {
  getAllConsultation,
  getConsultationById,
  getAllConsultationByUserId,
  updateConsultation,
  createConsultation,
};
