require('dotenv').config();

const {
  DB_USERNAME, DB_PASSWORD, DB_NAME, DB_HOST, DB_PORT, CLOUDSQL_INSTANCE_CONNECTION_NAME
} = process.env;

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
    dialect: 'mysql',
    dialectOptions:{
        socketPath : `/cloudsql/${CLOUDSQL_INSTANCE_CONNECTION_NAME}`,     
    },
    seederStorage: 'sequelize',
    logging: false,
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
