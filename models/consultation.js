'use strict';
const {
  Model
} = require('sequelize');
module.exports = (sequelize, DataTypes) => {
  class Consultation extends Model {
    /**
     * Helper method for defining associations.
     * This method is not a part of Sequelize lifecycle.
     * The `models/index` file will call this method automatically.
     */
    static associate(models) {
      this.belongsTo(models.User, { foreignKey: 'user_id'})
      this.belongsTo(models.User, { foreignKey: 'lawyer_id'})
    }
  }
  Consultation.init({
    title: DataTypes.STRING,
    description: DataTypes.TEXT,
    picture_URI: DataTypes.STRING,
    user_id: DataTypes.INTEGER,
    lawyer_id: DataTypes.INTEGER,
    date_created: DataTypes.DATE,
  }, {
    sequelize,
    modelName: 'Consultation',
  });
  return Consultation;
};