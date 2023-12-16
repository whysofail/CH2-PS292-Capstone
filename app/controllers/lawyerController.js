const { User, Role, LawyerTags, Tags, LawyerFee, sequelize } = require("../../models");
const { Op } = require("@sequelize/core");

const getLawyer = async (req, res) => {
  try {
    const role_id = req.query.role_id || 3;

    const users = await User.findAll({
      where: { role_id },
      attributes: ["id", "first_name", "last_name", "email","fee"],
      include: [
        {
          model: Role,
          as: "role",
          where: { id: role_id },
          attributes: ["name"],
        },
        {
          model: Tags,
          as: "lawyerTags",
          attributes: ["name", "description"],
          through: { attributes: [] },
        },
      ],
    });

    if (users.length === 0) {
      return res
        .status(404)
        .json({ msg: "No users found with the specified role_id" });
    }

    return res.status(200).json({
      msg: "Users found successfully",
      data: users,
    });
  } catch (error) {
    console.error("Error retrieving users:", error);
    return res.status(500).json({ msg: "Internal Server Error" });
  }
};

const searchLawyerByTag = async (req, res) => {
  try {
    const { tag } = req.query;

    if (!tag) {
      return res.status(400).json({
        msg: "Tag name is required in the request query parameters",
      });
    }

    const users = await User.findAll({
      attributes: ["id", "first_name", "last_name", "email","fee"],
      include: [
        {
          model: Role,
          as: "role",
          where: { id: 3 },
          attributes: ["name"],
        },
        {
          model: Tags,
          as: "filterLawyerTags",
          attributes: [],
          where: { name: { [Op.like]: `%${tag}%` } },
        },
        {
          model: Tags,
          as: "lawyerTags",
          attributes: ["name", "description"],
          through: { attributes: [] },
          seperate: true
        }
      ],
    });

    if (users.length === 0) {
      return res
        .status(404)
        .json({ msg: "No lawyers found with the specified tag" });
    }

    return res.status(200).json({
      msg: "Lawyers found successfully",
      data: users,
    });
  } catch (error) {
    console.error("Error searching for lawyers by tag:", error);
    return res.status(500).json({ msg: "Internal Server Error" });
  }
};

module.exports = {
  getLawyer,
  searchLawyerByTag,
};
