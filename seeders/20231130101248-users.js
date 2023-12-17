"use strict";
const bcrypt = require("bcryptjs");

const password = "123456";
const encryptedPassword = bcrypt.hashSync(password, 10);

module.exports = {
  async up(queryInterface, Sequelize) {
    await queryInterface.bulkInsert(
      "Users",
      [
        {
          first_name: "John",
          last_name: "Doe",
          email: "john.doe@example.com",
          password: encryptedPassword,
          role_id: 3,
          profile_picture: 'https://storage.googleapis.com/lawmate_user_bucket/20231216T123453.111Z_c99e7953450b46bf81120fae02d94b0c.jpg',
          fee: 100000
        },
        {
          first_name: "Jane",
          last_name: "Doe",
          email: "jane.doe@example.com",
          password: encryptedPassword,
          role_id: 3,
          profile_picture: 'https://storage.googleapis.com/lawmate_user_bucket/20231216T123453.111Z_c99e7953450b46bf81120fae02d94b0c.jpg',

          
          fee: 150000
        },
        // Add more users as needed
      ],
      {}
    );

    // Fetch users after inserting them
    const users = await queryInterface.sequelize.query("SELECT id from Users;");

    const userRows = users[0];

    // Fetch tags after inserting them
    const tags = await queryInterface.sequelize.query("SELECT id from Tags;");

    const tagRows = tags[0];

    const userTagsData = [];

    userRows.forEach((user) => {
      const randomTagCount = Math.floor(Math.random() * 3) + 1;

      for (let i = 0; i < randomTagCount; i++) {
        const randomTagIndex = Math.floor(Math.random() * tagRows.length);
        const tagId = tagRows[randomTagIndex].id;

        userTagsData.push({
          user_id: user.id,
          tags_id: tagId,
        });
      }
    });

    // Insert associations into LawyerTags
    await queryInterface.bulkInsert("LawyerTags", userTagsData, {});
  },

  async down(queryInterface, Sequelize) {
    // Delete associations from LawyerTags
    await queryInterface.bulkDelete("LawyerTags", null, {});

    // Delete users
    await queryInterface.bulkDelete("Users", null, {});
  },
};
