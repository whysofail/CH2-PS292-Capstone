const { GoogleAuth } = require("google-auth-library");

const { ML_URI } = process.env;
const targetAudience = ML_URI;

async function getIdTokenFromMetadataServer(req, res, next) {
  try {
    const googleAuth = new GoogleAuth();
    const client = await googleAuth.getIdTokenClient(targetAudience);
    const idToken = await client.idTokenProvider.fetchIdToken(targetAudience);
    req.idToken = idToken;
    next();
  } catch (error) {
    console.error("Error generating ID token:", error);
    res.status(500).json({ msg: "Internal Server Error" });
  }
}

module.exports = {
    getIdTokenFromMetadataServer
}
