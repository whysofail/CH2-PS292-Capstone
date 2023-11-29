FROM node:14.21.2-alpine

WORKDIR /app
ENV PORT 8080

COPY . .

RUN npm install

# Menjalankan skrip db:refresh
# RUN npm run db:refresh

EXPOSE 3306
EXPOSE 8080

# Menetapkan variabel lingkungan untuk Sequelize
ENV DB_USERNAME=root
ENV DB_PASSWORD=
ENV DB_NAME=lawmate
ENV DB_HOST=127.0.0.1
ENV DB_PORT=3306

CMD ["npm", "run", "dev"]
