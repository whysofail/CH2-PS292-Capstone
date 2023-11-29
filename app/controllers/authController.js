const bcrypt = require("bcryptjs");
const jwt = require("jsonwebtoken");
const { User } = require("../../models");

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
    expiresIn: "6h",
  });
  const refresh = jwt.sign(payload, process.env.REFRESH_TOKEN_SECRET, {
    expiresIn: "7d",
  });
  return [access, refresh];
}

const register = async (req, res) => {
  try {
    const { first_name, last_name, email, password, confirmationPassword } =
      req.body;

    if (
      !first_name ||
      !last_name ||
      !email ||
      !password ||
      !confirmationPassword
    ) {
      return res.status(400).json({ message: "All fields are required" });
    }

    if (password !== confirmationPassword) {
      return res.status(401).json({ message: "Passwords do not match" });
    }

    const encryptedPassword = await encryptPassword(password);
    const USER = 1;
    const role_id = USER;
    const user = await User.create({
      first_name,
      last_name,
      email,
      password: encryptedPassword,
      role_id,
    });

    return res.status(200).json({ message: "Registration successful" });
  } catch (err) {
    console.error("Registration error:", err);

    return res.status(500).json({
      error: {
        name: err.name,
        message: "An error occurred during registration",
      },
    });
  }
};

const login = async (req, res) => {
  const { email, password } = req.body;

  const user = await User.findOne({
    where: { email },
  });

  if (!user) {
    res.status(404).json({ message: "Email not found" });
    return;
  }

  const isPasswordCorrect = await checkPassword(user.password, password);

  if (!isPasswordCorrect) {
    res.status(401).json({ message: "Wrong password!" });
    return;
  }

  const token = createToken({
    user_id: user.user_id,
    first_name: user.first_name,
    last_name: user.last_name,
    email: user.email,
    role_id: user.role_id,
  });
  const accessToken = token[0];
  const refreshToken = token[1];
  await User.update(
    { refreshToken },
    {
      where: {
        id: user.id,
      },
    }
  );
  res.cookie("refreshToken", refreshToken, {
    httpOnly: true,
    maxAge: 24 * 60 * 60 * 1000,
  });
  res.status(201).json({
    message: "login success",
    user: {
      user_id: user.user_id,
      first_name: user.first_name,
      last_name: user.last_name,
      email: user.email,
      role_id: user.role_id,
      accessToken,
    },
  });
};

const whoAmI = async (req, res) => {
  res.status(200).json(req.user);
};

const logout = async (req, res) => {
  try {
    const refreshToken =
      req.body.refreshToken === undefined || req.body.refreshToken === null
        ? req.cookies.refreshToken
        : req.body.refreshToken;
    if (!refreshToken) {
      res.status(204).send("null");
      return;
    }
    const user = await User.findAll({
      where: {
        refreshToken,
      },
    });
    if (!user[0]) {
      res.status(204).send("notfound");
      return;
    }
    const userId = user[0].id;
    await User.update(
      { refreshToken: null },
      {
        where: {
          id: userId,
        },
      }
    );
    res.clearCookie("refreshToken");
    res.status(200).json("Log out success");
  } catch (error) {
    res.status(400).json({ msg: "Something went wrong" });
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
      const { email, createdAt, updatedAt, role_id } = user;
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
          expiresIn: "6h",
        }
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
  register,
  login,
  whoAmI,
  logout,
  refreshToken,
  onLost(_req, res) {
    res.status(404).json({
      status: "FAIL",
      message: "Route not found!",
    });
  },
  onError(err, _req, res, _next) {
    res.status(500).json({
      status: "ERROR",
      error: {
        name: err.name,
        message: err.message,
      },
    });
  },
};
