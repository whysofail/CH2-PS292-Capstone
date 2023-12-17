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
    expiresIn: "7d",
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
    const profile_picture = req.imagePublic_URI || null;

    if (
      !first_name ||
      !last_name ||
      !email ||
      !password ||
      !confirmationPassword 
    ) {
      return res.status(400).json({ msg: "All fields are required" });
    }

    if (password !== confirmationPassword) {
      return res.status(401).json({ msg: "Passwords do not match" });
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
      profile_picture,
    });

    return res.status(200).json({ msg: "Registration successful" });
  } catch (err) {
    console.error("Registration error:", err);

    return res.status(500).json({
      error: {
        name: err.name,
        msg: "An error occurred during registration",
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
    res.status(404).json({ msg: "Email not found" });
    return;
  }

  const isPasswordCorrect = await checkPassword(user.password, password);

  if (!isPasswordCorrect) {
    res.status(401).json({ msg: "Wrong password!" });
    return;
  }

  const token = createToken({
    user_id: user.id,
    first_name: user.first_name,
    last_name: user.last_name,
    email: user.email,
    role_id: user.role_id,
    profile_picture: user.profile_picture,
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
  res.status(200).json({
    msg: "login success",
    user: {
      user_id: user.id,
      first_name: user.first_name,
      last_name: user.last_name,
      email: user.email,
      role_id: user.role_id,
      profile_picture: user.profile_picture,
      accessToken,
    },
  });
};

const whoAmI = async (req, res) => {
  res.status(200).json(req.user);
};

const logout = async (req, res) => {
  try {
    res.clearCookie("refreshToken");
    res.status(200).json({ msg: "Log out success" });
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
          user_id: user.id,
          email: user.email,
          createdAt: user.createdAt,
          updatedAt: user.updatedAt,
          role_id: user.role_id,
        },
        process.env.ACCESS_TOKEN_SECRET,
        {
          expiresIn: "7d",
        }
      );
      res.json({
        user_id: userId,
        email,
        createdAt,
        updatedAt,
        role_id,
        accessToken,
      });
    });
  } catch (err) {
    res.status(422).json({
      error: {
        name: err.name,
        msg: err.msg,
      },
    });
  }
};

const updateUser = async (req, res) => {
  try {
    const { first_name, last_name, email, password } = req.body;
    const profile_picture = req.imagePublic_URI || null;
    const user_id = req.user.id;

    let updatedFields = {};

    if (first_name) updatedFields.first_name = first_name;
    if (last_name) updatedFields.last_name = last_name;
    if (email) updatedFields.email = email;
    if (password) updatedFields.password = await encryptPassword(password);
    if (profile_picture) updatedFields.profile_picture = profile_picture;

    const updatedUser = await User.update(updatedFields, {
      where: {
        id: user_id,
      },
    });

    if (updatedUser) {
      return res.status(200).json({ msg: "User data updated successfully" });
    } else {
      return res.status(400).json({ msg: "Failed to update user data" });
    }
  } catch (err) {
    console.error("Update error:", err);

    return res.status(500).json({
      error: {
        name: err.name,
        msg: "An error occurred during updating",
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
  updateUser, // Add this line
  onLost(_req, res) {
    res.status(404).json({
      status: "FAIL",
      msg: "Route not found!",
    });
  },
  onError(err, _req, res, _next) {
    res.status(500).json({
      status: "ERROR",
      error: {
        name: err.name,
        msg: err.message,
      },
    });
  },
};