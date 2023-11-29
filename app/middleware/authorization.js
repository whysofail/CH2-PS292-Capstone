const jwt = require('jsonwebtoken');
const { User } = require('../utils/db');

const authorize = async (req, res, next) => {
  try {
    const bearerToken = req.headers.authorization;
    const token = bearerToken.split('Bearer ')[1];
    const tokenPayload = jwt.verify(token, process.env.ACCESS_TOKEN_SECRET);
    req.user = await User.findByPk(tokenPayload.user_id);
    next();
  } catch (err) {
    res.status(401).json({
      msg: 'Unauthorized',
    });
  }
};

const isAdmin = async (req, res, next) => {
  const auth = req.user.roleId;
  if (auth !== 1) {
    res.status(403).json({ msg: 'member unauthorized' })
    return;
  }
  next();
};

module.exports = { authorize, isAdmin };
