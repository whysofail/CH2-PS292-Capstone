'use strict';
const { Model } = require('sequelize');

module.exports = (sequelize, DataTypes) => {
  class LawyerTags extends Model {
    static associate(models) {
      this.belongsTo(models.User, {
        foreignKey: 'user_id',
        onDelete: 'CASCADE',
      });
      this.belongsTo(models.Tags, {
        foreignKey: 'tags_id',
        onDelete: 'CASCADE',
        as: 'tag',
      });
    }
  }

  LawyerTags.init(
    {
      user_id: DataTypes.INTEGER,
      tags_id: DataTypes.INTEGER,
    },
    {
      scopes: {
        getUser: {
          attributes: []
        }
      },
      timestamps: false,
      sequelize,
      modelName: 'LawyerTags',
    }
  );

  return LawyerTags;
};
