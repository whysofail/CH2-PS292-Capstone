FROM node:14.21.2-alpine
WORKDIR /app
ENV PORT 8080
COPY . .
RUN npm install
# RUN npm run db:refresh
EXPOSE 3306
EXPOSE 8080
CMD ["sh", "-c", "npm run db:refresh && npm run dev"]