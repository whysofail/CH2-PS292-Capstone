const { sequelize } = require("../../models");

const checkDatabaseConn = async (req, res) => {
  try {
    await sequelize.authenticate();
    return res.status(200).json({msg: 'Database connection has been established successfully.'});
  } catch (error) {
    return res.status(500).json({msg: 'Unable to connect to the database:', error});
  }
};


module.exports = { checkDatabaseConn }