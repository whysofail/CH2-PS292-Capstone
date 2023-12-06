const axios = require("axios");

const { ML_URI } = process.env;

const requestOptions = (idToken) => ({
  baseURL: ML_URI,
  headers: {
    'X-Serverless-Authorization' : `Bearer ${idToken}`
  }
})

const getServerStatus = async (req, res) => {
  const idToken = req.idToken;
  try {
    const response = await axios.get(`/`, {
      ...requestOptions(idToken),
    });
    if (response.status === 200) {
      return res.status(200).json({msg: response.data });
    } else {
      return res
        .status(response.status)
        .json({ status: "Error", messamsgge: "Unexpected status code" });
    }
  } catch (error) {
    console.error("Error making HTTP request:", error.message);
    return res.status(500).json({ status: "Error", msg: "Internal Server Error" });
  }
};

const getChat = async (req, res) => {
  const { user_input } = req.body;
  const idToken = req.idToken;

  console.log(`Id Token: ${idToken}`);

  try {
    const response = await axios(
      options("chat", "POST", { user_input }, idToken)
    );
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
