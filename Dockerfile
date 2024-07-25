# Use the official Gradle image to create a build artifact
# This image has Gradle and JDK pre-installed
FROM gradle:7.4.2-jdk11 AS build
WORKDIR /app

# Copy the Gradle project files
COPY build.gradle settings.gradle /app/
COPY src /app/src

# Build the project
RUN gradle build -x test

# Use the official OpenJDK image as a base for the running app
FROM openjdk:11-jre-slim
WORKDIR /app

# Copy the JAR file from the previous stage
COPY --from=build /app/build/libs/demo-0.0.1-SNAPSHOT.jar app.jar

# Specify the command to run the app
ENTRYPOINT ["java", "-jar", "app.jar"]
