'use strict';
const {
  Model
} = require('sequelize');
module.exports = (sequelize, DataTypes) => {
  class LawyerTags extends Model {
    /**
     * Helper method for defining associations.
     * This method is not a part of Sequelize lifecycle.
     * The `models/index` file will call this method automatically.
     */
    static associate(models) {

    }
  }
  LawyerTags.init({
    user_id: DataTypes.INTEGER,
    tags_id: DataTypes.INTEGER
  }, {
    timestamps: false,
    sequelize,
    modelName: 'LawyerTags',
  });
  return LawyerTags;
};