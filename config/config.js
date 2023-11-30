require('dotenv').config();

const {
  DB_USERNAME, DB_PASSWORD, DB_NAME, DB_HOST, DB_PORT,
} = process.env;

console.log(DB_USERNAME, DB_PASSWORD, DB_NAME, DB_HOST, DB_PORT,)
module.exports = {
  development: {
    username: DB_USERNAME,
    password: DB_PASSWORD,
    database: DB_NAME,
    host: DB_HOST,
    port: DB_PORT,
    dialect: 'mysql',
    seederStorage: 'sequelize',
  },
  test: {
    username: DB_USERNAME,
    password: DB_PASSWORD,
    database: DB_NAME,
    host: DB_HOST,
    port: DB_PORT,
    dialect: 'mysql',
    seederStorage: 'sequelize',
  },
  production: {
    username: DB_USERNAME,
    password: DB_PASSWORD,
    database: DB_NAME,
    host: DB_HOST,
    port: DB_PORT,
    dialect: 'mysql',
    seederStorage: 'sequelize',
  },
};
