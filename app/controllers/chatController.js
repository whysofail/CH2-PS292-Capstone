const axios = require("axios");

const { ML_URI } = process.env;

const options = (path, method, bodyData, idToken) => {
  const requestOptions = {
    url: `${ML_URI}/${path}`,
    method,
    headers: {
      "Content-Type": "application/json",
      "X-Serverless-Authorization": `Bearer ${idToken}`,
    },
    data: bodyData,
    json: true,
  };

  return requestOptions;
};

const getServerStatus = async (req, res) => {
  const idToken = req.idToken
  try {
    const response = await axios(
      options('', "GET", '', idToken)
    )  
    return res.status(response.status).json(response.data)
  } catch (error) {
    return res.status(response.status).json(response.data);
  }
}

const getChat = async (req, res) => {
  const { user_input } = req.body;
  const idToken = req.idToken; // Move idToken retrieval here

  console.log(`Id Token: ${idToken}`);

  try {
    const response = await axios(
      options("chat", "POST", { user_input }, idToken)
    );
    return res.status(response.status).json(response.data);
  } catch (error) {
    console.error("Error making request:", error.message);
    return res.status(500).json({ error: "Internal Server Error" });
  }
};

module.exports = {
  getServerStatus,
  getChat,
};
