FROM gradle:7.4.2-jdk17 AS build
WORKDIR /app

COPY build.gradle settings.gradle /app/
COPY src /app/src

RUN gradle build -x test

FROM openjdk:17-jre-slim
WORKDIR /app

COPY --from=build /app/build/libs/demo-0.0.1-SNAPSHOT.jar app.jar

ENTRYPOINT ["java", "-jar", "app.jar"]
