const { User } = require("../../models");

const emailExist = async (req, res, next) => {
  try {
    const email = req.body.email.toLowerCase();
    const getEmail = await User.findOne({ where: { email } });
    if (getEmail) {
      return res.status(400).json({ msg: "email is already used" });
    }
    next();
  } catch (error) {
    console.log(error)
  }
};

const emailNull = (req, res, next) => {
  const { email } = req.body;
  if (!email) {
    res.status(400).json({ msg: "email can`t be empty" });
    return;
  }
  next();
};

module.exports = {
  emailExist,
  emailNull,
};
