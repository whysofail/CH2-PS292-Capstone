const express = require("express");
const cors = require("cors")
require("dotenv").config();


const app = express();
const { PORT = 8000 } = process.env;

app.use(cors())
app.get("/", (req, res) => {
  res.status(200).json({ msg: "Lawmate backend status : up" });
});

app.get("*", (req, res) => {
  res.status(404).json({ msg: "Not found" });
});


app.listen(PORT, () => {
  console.log(`Listening on port, http://127.0.0.1:${PORT}`);
});
