const request = require("request");

const { ML_URI } = process.env;

const chat = async (req, res) => {
  const { user_input } = req.body;

  const options = {
    uri: `${ML_URI}/chat`,
    method: "POST",
    headers: {
      "Content-Type": "application/json",
    },
    body: JSON.stringify({ user_input }),
  };

  try {
    const result = await new Promise((resolve, reject) => {
      request(options, (error, response, body) => {
        if (error) {
          reject(error);
        } else {
          try {
            const responseBody = JSON.parse(body);
            resolve({ response, body: responseBody });
          } catch (error) {
            reject(error);
          }
        }
      });
    });

    res.status(result.response.statusCode).json( result.body );
  } catch (error) {
    console.error("Error in the try-catch block:", error);

    if (error instanceof SyntaxError) {
      // Handle JSON parse errors
      res.status(500).json({ error: "Invalid JSON response from the server" });
    } else {
      // Handle other errors
      res.status(500).json({ error: "Internal Server Error" });
    }
  }
};

module.exports = {
  chat,
};
