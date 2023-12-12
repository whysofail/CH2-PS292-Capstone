const axios = require("axios");

const { ML_URI } = process.env;

const requestOptions = (idToken) => ({
  headers: {
    "Metadata-Flavor": "Google",
    "Content-Type": "application/json",
    "X-Serverless-Authorization": `Bearer ${idToken}`,
  },
});

const getServerStatus = async (req, res) => {
  const idToken = req.idToken;
  try {
    const response = await axios.get(`${ML_URI}/`, {
      ...requestOptions(idToken),
    });
    if (response.status === 200) {
      return res.status(200).json(response.data);
    } else {
      return res
        .status(response.status)
        .json({ status: "Error", messamsgge: "Unexpected status code" });
    }
  } catch (error) {
    console.error("Error making HTTP request:", error.message);
    return res
      .status(500)
      .json({ status: "Error", msg: "Internal Server Error" });
  }
};

const getChat = async (req, res) => {
  const { user_input } = req.body;
  const idToken = req.idToken;

  try {
    const response = await axios.post(`${ML_URI}/chat`, {
      ...requestOptions(idToken),
      user_input: user_input,
    });
    if (response.status === 200) {
      return res.status(200).json(response.data);
    } else {
      return res
        .status(response.status)
        .json({ status: "Error", msg: "Unexpected status code" });
    }
  } catch (error) {
    console.error(error);
    console.error("Error making HTTP request:", error.message);
    return res
      .status(500)
      .json({ status: "Error", msg: "Internal Server Error" });
  }
};

module.exports = {
  getServerStatus,
  getChat,
};
