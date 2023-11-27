require("dotenv").config();
const express = require("express");
const cors = require("cors")
const bodyParser = require('body-parser')
const router = require('./app/routes/router')
// require('./app/utils/db')

const app = express();
const { PORT = 8080,} = process.env;


app.use(cors())
app.use(bodyParser.json());
app.use(bodyParser.urlencoded({ extended: true }));
app.use(router)

app.use(express.json())
app.get("/", (req, res) => {
  res.status(200).json({ msg: "Lawmate backend status : up" });
});

app.get("*", (req, res) => {
  res.status(404).json({ msg: "Not found" });
});


app.listen(PORT, () => {
  console.log(`Listening on port, http://127.0.0.1:${PORT}`);
});
