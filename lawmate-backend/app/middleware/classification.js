const axios = require("axios");

const { ML_URI } = process.env;

const requestOptions = (idToken) => ({
  headers: {
    "Metadata-Flavor": "Google",
    "Content-Type": "application/json",
    "X-Serverless-Authorization": `Bearer ${idToken}`,
  },
});

const getImageClassification = async (req, res, next) => {
  const idToken = req.idToken;
  const image_URI = req.imagePublic_URI
  if(!image_URI){
    console.log('No file attached')
    return next()
  }
  try {
    const response = await axios.post(`${ML_URI}/images`, {
      ...requestOptions(idToken),
      image_url: image_URI,
    });
    if (response.status === 200) {
      req.imageType = response.data.msg
      next()
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
  getImageClassification
};
