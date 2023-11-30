const { User, Role, LawyerTags, Tags } = require('../../models');

const getLawyers = async (req, res) => {
  try {
    // Cari role dengan id 3 (dianggap sebagai role untuk lawyer)
    const lawyerRole = await Role.findOne({
      where: { id: 3 },
    });

    if (!lawyerRole) {
      return res.status(404).json({ message: 'Role for lawyers not found' });
    }

    // Cari pengguna dengan role_id 3 (lawyer)
    const lawyers = await User.findAll({
      where: { role_id: lawyerRole.id },
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

    return res.status(200).json({ lawyers });
  } catch (error) {
    console.error('Error retrieving lawyers:', error);
    return res.status(500).json({ message: 'Internal Server Error' });
  }
};

module.exports = {
  getLawyers,
};
