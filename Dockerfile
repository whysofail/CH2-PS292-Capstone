# syntax=docker/dockerfile:1

# Use an official Node.js runtime as a base image
ARG NODE_VERSION=18.12.1
FROM node:${NODE_VERSION}-alpine

# Use production node environment by default.
ENV NODE_ENV development

WORKDIR /usr/src/app

# Download dependencies as a separate step to take advantage of Docker's caching.
# Leverage a cache mount to /root/.npm to speed up subsequent builds.
# Leverage a bind mounts to package.json and package-lock.json to avoid having to copy them into
# into this layer.
RUN --mount=type=bind,source=package.json,target=package.json \
    --mount=type=bind,source=package-lock.json,target=package-lock.json \
    --mount=type=cache,target=/root/.npm \
    npm ci --omit=dev

# Copy the rest of the source files into the image.
COPY . .
COPY .env ./
COPY config/ ./config/
COPY migrations/ ./migrations/
COPY models/ ./models/
COPY seeders/ ./seeders/

# Install global dependencies
RUN npm i -g sequelize-cli

# Install local dependencies
RUN npm i

# Database setup
RUN npx sequelize-cli db:migrate && \
    npx sequelize-cli db:seed:undo && \
    npx sequelize-cli db:seed:all

EXPOSE 8080

# Run the application.
CMD npm run dev
