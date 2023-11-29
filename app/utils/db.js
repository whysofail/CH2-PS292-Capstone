// const { Sequelize, DataTypes } = require("sequelize");

// const { DB_USERNAME, DB_PASSWORD, DB_NAME, DB_HOST, DB_PORT } = process.env;

// const sequelize = new Sequelize(DB_NAME, DB_USERNAME, DB_PASSWORD, {
//   define : {
//     timestamps : false
//   },
//   host: DB_HOST,
//   port: DB_PORT,
//   dialect: "mysql",
// });

// const Role = sequelize.define("Role", {
//   role_id: {
//     type: DataTypes.INTEGER,
//     primaryKey: true,
//     autoIncrement: true,
//   },
//   rolename: {
//     type: DataTypes.STRING,
//     allowNull: false,
//   },
// });

// const User = sequelize.define("User", {
//   user_id: {
//     type: DataTypes.INTEGER,
//     primaryKey: true,
//     autoIncrement: true,
//   },
//   first_name: {
//     type: DataTypes.STRING,
//     allowNull: false,
//   },
//   last_name: {
//     type: DataTypes.STRING,
//     allowNull: false,
//   },
//   email: {
//     type: DataTypes.STRING,
//     allowNull: false,
//   },
//   password: {
//     type: DataTypes.STRING,
//     allowNull: false,
//   },
// });

// const Tags = sequelize.define("Tags", {
//   tag_id: {
//     type: DataTypes.INTEGER,
//     primaryKey: true,
//     autoIncrement: true,
//   },
//   name: {
//     type: DataTypes.STRING,
//     allowNull: false,
//   },
//   description: {
//     type: DataTypes.STRING,
//   },
// });

// const LawyerTags = sequelize.define("LawyerTags", {
//   lawyertags_id: {
//     type: DataTypes.INTEGER,
//     primaryKey: true,
//     autoIncrement: true,
//   },
// });

// Tags.belongsToMany(User, { through: LawyerTags, foreignKey: "tag_id" });
// User.belongsTo(Role, { foreignKey: "role_id" });

// const main = async () => {
//   try {
//     await sequelize.sync({force: true}).then(() => {
//       console.log("Tabel telah disinkronkan");
//     });
//   } catch (error) {
//     console.error(error)
//   }
// };

// main()

// module.exports = { Role, User, Tags, LawyerTags, sequelize };
