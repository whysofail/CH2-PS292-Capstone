const request = require("request");

const { ML_URI } = process.env;

const options = (path, method, bodyData) => {
  const requestOptions = {
    uri: `${ML_URI}/${path}`,
    method,
    headers: {
      "Content-Type": "application/json",
      "X-Serverless-Authorization": `Bearer ${idToken}`,
    },
    json: true,
  };

  if (bodyData) {
    requestOptions.body = bodyData;
  }

  return requestOptions;
};

const idToken = req.idToken;

const chat = async (req, res) => {
  const { user_input } = req.body;

  try {
    const result = await request(options("/chat", "POST", user_input));
    return res.status(200).json(result);
  } catch (error) {
    console.error(error);
    return res.status(500).json(error);
  }
};

module.exports = {
  chat,
};
