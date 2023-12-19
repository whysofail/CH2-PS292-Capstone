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

const getAllConsultationByLawyerId = async (req, res) => {
  const { user } = req;
  try {
    const consultations = await Consultation.findAll({
      where: {
        lawyer_id: user.id,
      },
      include: [{
        model: User,
        as: 'user',
        attributes: ['first_name', 'last_name'], 
      }],
    });
    if (!consultations) {
      return res.status(400).json({ msg: "No consultations found for this lawyer" });
    }
    return res.status(200).json({ msg: consultations });
  } catch (error) {
    console.error(error);
    return res.status(500).json({ msg: "Internal server error." });
  }
};



const getConsultationById = async (req, res) => {
  try {
    const { id } = req.params;
    const user = req.user;
    console.log(id)
    const consultation = await Consultation.findOne({
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
  const { user, imagePublic_URI, extracted_text, imageType } = req;
  const { lawyer_id } = req.query;
  const { title, description } = req.body;
  const picture_URI = imagePublic_URI || null;
  
  let ekstrakteks = null

  if (!lawyer_id) {
    return res.status(400).json({ msg: "No lawyer id" });
  }
  try {
    const lawyer = await User.findByPk(lawyer_id);
    if (!lawyer) {
      return res
        .status(400)
        .json({ msg: `No lawyer found with id : ${lawyer_id}` });
    }
    if (picture_URI !== null && imageType === "chat") {
      ekstrakteks = extracted_text[0]['description']
    }
    
    const consultation = await Consultation.create({
      title,
      description,
      picture_URI,
      user_id: user.id,
      lawyer_id,
      ekstrakteks,
    });

    return res.status(200).json({ msg: consultation });
  } catch (error) {
    console.error(error);
    return res
      .status(500)
      .json({ error: "Internal server error", details: error.message });
  }
};


const updateConsultation = async (req, res) => {
  try {
    const { user, imagePublic_URI, extracted_text, imageType } = req;
    const picture_URI = imagePublic_URI || null;
    let ekstrakteks = null
    const id = req.params.id;
    let consultation = await Consultation.findByPk(id, {
      where: {
        user_id: user.id,
      },
    });
    if (picture_URI !== null && imageType === "chat") {
      ekstrakteks = extracted_text[0]['description']
    }
    
    if (!consultation) {
      return res.status(400).json({
        msg: "Consultation not found or you dont have permission to see this.",
      });
    }
    const updatedData = req.body;
    updatedData.ekstrakteks = ekstrakteks
    updatedData.picture_URI = picture_URI
    consultation = Object.assign(consultation, updatedData);
    consultationUpdate = await Consultation.update(updatedData, {
      where : {
        id 
      },
    })
    res.status(200).json({ msg: "Update success", consultation});
  } catch (error) {
    console.error("Error updating consultation:", error);
    res.status(500).json({ msg: "Internal Server Error" });
  }
};

module.exports = {
  getAllConsultation,
  getConsultationById,
  getAllConsultationByUserId,
  getAllConsultationByLawyerId,
  updateConsultation,
  createConsultation,
};
