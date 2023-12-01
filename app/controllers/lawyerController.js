const { User, LawyerTags, Tags } = require('../../models');

const getUsersByRole = async (req, res) => {
  try {
    const { role_id } = req.query;

    if (!role_id) {
      return res.status(400).json({ message: 'Role ID is required in the query parameters' });
    }

    // Cari pengguna dengan role_id yang diberikan
    const users = await User.findAll({
      where: { role_id },
      include: [
        {
          model: LawyerTags,
          as: 'lawyerTags',
          include: [
            {
              model: Tags,
              as: 'tag',
            },
          ],
        },
      ],
    });

    if (users.length === 0) {
      return res.status(404).json({ message: 'No users found with the specified role_id' });
    }

    return res.status(200).json({ 
      message: 'Users found successfully', 
      users 
    });
  } catch (error) {
    console.error('Error retrieving users:', error);
    return res.status(500).json({ message: 'Internal Server Error' });
  }
};

module.exports = {
  getUsersByRole,
};
