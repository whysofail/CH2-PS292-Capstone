"use strict";
const { Model } = require("sequelize");
module.exports = (sequelize, DataTypes) => {
  class User extends Model {
    static associate(models) {
      this.belongsTo(models.Role, {
        foreignKey: "role_id",
        as: "role",
        onDelete: "CASCADE",
      });
      this.belongsToMany(models.Tags, {
        through: "LawyerTags",
        foreignKey: "user_id",
        as: "lawyerTags",
      });
      this.belongsToMany(models.Tags, {
        through: "LawyerTags",
        foreignKey: "user_id",
        as: "filterLawyerTags",
      });
    }
  }

  User.init(
    {
      first_name: DataTypes.STRING,
      last_name: DataTypes.STRING,
      email: DataTypes.STRING,
      password: DataTypes.STRING,
      role_id: DataTypes.INTEGER,
    },
    {
      timestamps: false,
      sequelize,
      modelName: "User",
    }
  );

  return User;
};
