npx sequelize-cli db:migrate &&
npx sequelize-cli db:seed:undo &&
npx sequelize-cli db:seed:all &&
npm run start