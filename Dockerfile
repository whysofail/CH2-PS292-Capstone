# syntax=docker/dockerfile:1

# Use an official Node.js runtime as a base image
ARG NODE_VERSION=18.12.1
FROM node:${NODE_VERSION}-alpine

# Use production node environment by default.
ENV NODE_ENV test

WORKDIR /usr/src/app

# Download dependencies as a separate step to take advantage of Docker's caching.
# Leverage a cache mount to /root/.npm to speed up subsequent builds.
# Leverage a bind mounts to package.json and package-lock.json to avoid having to copy them into
# into this layer.


# Copy the rest of the source files into the image.
COPY package*.json ./
COPY . .
COPY config/ ./config/
COPY migrations/ ./migrations/
COPY models/ ./models/
COPY seeders/ ./seeders/

# Install global dependencies
RUN npm i -g sequelize-cli

# Install local dependencies
RUN npm i

EXPOSE 8080

# Run the application.
RUN chmod +x startup.sh
CMD [ "./startup.sh" ]
