'use strict';

/** @type {import('sequelize-cli').Migration} */
module.exports = {
  async up (queryInterface, Sequelize) {
    await queryInterface.bulkInsert('Tags', [
      {
        name: 'Pelecehan Seksual',
        description: 'Spesialisai dalam bidang Pelecehan Seksual',
      },
      {
        name: 'Hutang Piutang',
        description: 'Spesialisai dalam bidang Hutang Piutang',
      },
      
    ], {});
  },

  async down (queryInterface, Sequelize) {
    await queryInterface.bulkDelete('Tags', null, {});
  }
};
