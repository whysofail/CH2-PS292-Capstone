const request = require("request");

const { ML_URI } = process.env;

const chat = async (req, res) => {
  const { user_input } = req.body;
  const idToken = req.idToken;

  const options = {
    uri: `${ML_URI}/chat`,
    method: "POST",
    headers: {
      "Content-Type": "application/json",
      Authorization: `Bearer ${idToken}`,
    },
    json: true,
    body: { user_input },
  };

  try {
    const result = await request(options);

    res.status(result.statusCode).json(result);
  } catch (error) {
    console.error("Error in the try-catch block:", error);

    if (error.statusCode) {
      // Handle non-2xx status codes
      res.status(error.statusCode).json(error.error);
    } else {
      // Handle other errors
      res.status(500).json({ error: "Internal Server Error" });
    }
  }
};

module.exports = {
  chat,
};
