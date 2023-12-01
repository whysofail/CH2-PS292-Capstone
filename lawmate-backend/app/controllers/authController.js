/* eslint-disable max-len */
/* eslint-disable no-unused-vars */

const bcrypt = require('bcryptjs');
const jwt = require('jsonwebtoken');
const randomstring = require('randomstring');
const { Op } = require('sequelize');
const { User, UserEmailConfirmation } = require('../../models');

const SALT = 10;


function encryptPassword(password) {
  return new Promise((resolve, reject) => {
    bcrypt.hash(password, SALT, (err, encryptedPassword) => {
      if (err) {
        reject(err);
        return;
      }
      resolve(encryptedPassword);
    });
  });
}

function checkPassword(encryptedPassword, password) {
  return new Promise((resolve, reject) => {
    bcrypt.compare(password, encryptedPassword, (err, isPasswordCorrect) => {
      if (err) {
        reject(err);
        return;
      }
      resolve(isPasswordCorrect);
    });
  });
}

function createToken(payload) {
  const access = jwt.sign(payload, process.env.ACCESS_TOKEN_SECRET, {
    expiresIn: '6h',
  });
  const refresh = jwt.sign(payload, process.env.REFRESH_TOKEN_SECRET, {
    expiresIn: '7d',
  });
  return [access, refresh];
}


const register = async (req, res) => {
  const email = req.body.email.toLowerCase();
  const {
    name, password, confirmationPassword, birthDate, gender, phone,
  } = req.body;
 
  if (password !== confirmationPassword) {
    res.status(401).json({ message: 'password doesn`t match' });
    return;
  }
  try {
    const encryptedPassword = await encryptPassword(password);

    const user = await User.create({
      name,
      email,
      encryptedPassword,
      birthDate: new Date(birthDate).toISOString(),
      gender,
      phone,
      roleId: role,
    });
    res.status(200).json('Register success');
  } catch (err) {
    res.status(400).json({ err: { name: err.name, message: err.message } });
  }
};

const login = async (req, res) => {
  const email = req.body.email.toLowerCase();
  const { password } = req.body;

  const user = await User.findOne({
    where: { email },
  });

  if (!user) {
    res.status(404).json({ message: 'Email not found' });
    return;
  }

  const isPasswordCorrect = await checkPassword(
    user.encryptedPassword,
    password,
  );

  if (!isPasswordCorrect) {
    res.status(401).json({ message: 'Wrong password!' });
    return;
  }

  const token = createToken({
    id: user.id,
    name: user.name,
    image: user.image,
    email: user.email,
    birthDate: user.birthDate,
    gender: user.gender,
    phone: user.phone,
    roleId: user.roleId,
    createdAt: user.createdAt,
    updatedAt: user.updatedAt,
  });
  const accesstToken = token[0];
  const refreshToken = token[1];
  await User.update(
    { refreshToken },
    {
      where: {
        id: user.id,
      },
    },
  );
  res.cookie('refreshToken', refreshToken, {
    httpOnly: true,
    maxAge: 24 * 60 * 60 * 1000,
  });
  res.status(201).json({
    message: 'login success',
    user: {
      id: user.id,
      email: user.email,
      accesstToken,
    },
  });
};

const whoAmI = async (req, res) => {
  res.status(200).json(req.user);
};

const logout = async (req, res) => {
  try {
    const refreshToken = req.body.refreshToken === undefined || req.body.refreshToken === null
      ? req.cookies.refreshToken
      : req.body.refreshToken;
    if (!refreshToken) {
      res.status(204).send('null');
      return;
    }
    const user = await User.findAll({
      where: {
        refreshToken,
      },
    });
    if (!user[0]) {
      res.status(204).send('notfound');
      return;
    }
    const userId = user[0].id;
    await User.update(
      { refreshToken: null },
      {
        where: {
          id: userId,
        },
      },
    );
    res.clearCookie('refreshToken');
    res.status(200).json('Log out success');
  } catch (error) {
    res.status(400).json({ msg: 'Something went wrong' });
  }
};

const refreshToken = async (req, res) => {
  try {
    const refresh = req.cookies.refreshToken;
    if (!refresh) {
      res.sendStatus(401);
      return;
    }
    const user = await User.findOne({
      where: {
        refreshToken: refresh,
      },
    });
    if (!user) {
      res.sendStatus(403);
      return;
    }
    jwt.verify(refresh, process.env.REFRESH_TOKEN_SECRET, (err, decoded) => {
      if (err) {
        res.sendStatus(403);
        return;
      }
      const userId = user.id;
      const {
        email, createdAt, updatedAt, roleId,
      } = user;
      const accessToken = jwt.sign(
        {
          id: user.id,
          name: user.name,
          image: user.image,
          email: user.email,
          birthDate: user.birthDate,
          gender: user.gender,
          phone: user.phone,
          roleId: user.roleId,
          createdAt: user.createdAt,
          updatedAt: user.updatedAt,
        },
        process.env.ACCESS_TOKEN_SECRET,
        {
          expiresIn: '6h',
        },
      );
      res.json({
        userId,
        email,
        accessToken,
      });
    });
  } catch (err) {
    res.status(422).json({
      error: {
        name: err.name,
        message: err.message,
      },
    });
  }
};



module.exports = {
  handleRegisterGoogle,
  handleLoginGoogle,
  handleGoogleAuthUrl,
  handleGoogleAuthCb,
  verifyIdToken,
  handleEmailVerify,
  register,
  registerTest,
  registerAdmin,
  login,
  whoAmI,
  logout,
  refreshToken,
  onLost(_req, res) {
    res.status(404).json({
      status: 'FAIL',
      message: 'Route not found!',
    });
  },
  onError(err, _req, res, _next) {
    res.status(500).json({
      status: 'ERROR',
      error: {
        name: err.name,
        message: err.message,
      },
    });
  },
};