const jwt = require('jsonwebtoken');
const { User } = require("../../models");

const authorize = async (req, res, next) => {
  try {
    const bearerToken = req.headers.authorization;

    if (!bearerToken || !bearerToken.startsWith('Bearer ')) {
      return res.status(401).json({ error: 'Unauthorized: Missing or invalid token' });
    }

    const token = bearerToken.split('Bearer ')[1];
    const tokenPayload = jwt.verify(token, process.env.ACCESS_TOKEN_SECRET);
    const user = await User.findByPk(tokenPayload.user_id);

    if (!user) {
      return res.status(401).json({ error: 'Unauthorized: User not found' });
    }

    req.user = user;
    next();
  } catch (err) {
    console.error('Authorization error:', err);
    res.status(401).json({ error: 'Unauthorized: Invalid token' });
  }
};

const roles = {
  user : 1,
  admin: 2,
  lawyer: 3
}

const isAdmin = async (req, res, next) => {
  const role = req.user.roleId;
  if (role !== roles.admin) {
    res.status(403).json({ msg: 'Unauthorized' })
    return;
  }
  next();
};

const isLawyer = async (req, res, next) => {
  const role = req.user.roleId
  if(role !== roles.lawyer){
    res.status(403).json({msg : 'Unauthorized'})
    return
  }
  next()
}

module.exports = { authorize, isAdmin, isAdmin };
