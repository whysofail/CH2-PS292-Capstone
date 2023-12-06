const axios = require("axios");

const { ML_URI } = process.env;

const options = (path, method, bodyData, idToken) => {
  const requestOptions = {
    url: `${ML_URI}/`+path,
    method,
    headers: {
      "Content-Type": "application/json",
      "X-Serverless-Authorization": `Bearer ${idToken}`,
    },
    data: bodyData,
  };

  return requestOptions;
};

const getServerStatus = async (req, res) => {
  const idToken = req.idToken;
  try {
    const response = await axios(options("", "GET", "", idToken));
    return res.status(200).json(response.data);
  } catch (error) {
    console.error("Error getting server status:", error);
    return res.status(500).json({ error: "Internal Server Error" });
  }
};

const getChat = async (req, res) => {
  const { user_input } = req.body;
  const idToken = req.idToken;

  console.log(`Id Token: ${idToken}`);

  try {
    const response = await axios(options("chat", "POST", { user_input }, idToken));
    console.log(response.data);
    return res.status(200).json(response.data);
  } catch (error) {
    console.error("Error getting chat:", error.message);
    return res.status(500).json({ error: "Internal Server Error" });
  }
};

module.exports = {
  getServerStatus,
  getChat,
};
