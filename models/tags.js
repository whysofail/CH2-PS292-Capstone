'use strict';
const { Model } = require('sequelize');

module.exports = (sequelize, DataTypes) => {
  class Tags extends Model {
    static associate(models) {
      this.belongsToMany(models.User, {
        through: 'LawyerTags',
        foreignKey: 'tags_id',
        as: 'users',
      });
    }
  }

  Tags.init(
    {
      name: DataTypes.STRING,
      description: DataTypes.STRING,
    },
    {
      scopes:{
        getUserLawyer: {
          
        }
      },
      timestamps: false,
      sequelize,
      modelName: 'Tags',
    }
  );

  return Tags;
};
