'use strict';

/** @type {import('sequelize-cli').Migration} */
module.exports = {
  async up (queryInterface, Sequelize) {
    /**
     * Add seed commands here.
     *
     * Example:
     * await queryInterface.bulkInsert('People', [{
     *   name: 'John Doe',
     *   isBetaMember: false
     * }], {});
    */
   await queryInterface.bulkInsert('Consultations',[
    {
      title: 'Pelecehan seksual di tempat kerja',
      description : 'Saya mengalami pelecehan seksual di tempat kerja di Perusahaan ABC oleh kolega saya',
      picture_URI : 'https://storage.googleapis.com/lawmate_user_bucket/0094e734ea512b42353bb2abe5a7a514.jpg',
      user_id : 1,
      lawyer_id : 1,
    }
   ])
  },

  async down (queryInterface, Sequelize) {
    /**
     * Add commands to revert seed here.
     *
     * Example:
     * await queryInterface.bulkDelete('People', null, {});
     */
  }
};
