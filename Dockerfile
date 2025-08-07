# syntax=docker/dockerfile:1

FROM gradle:8.3-jdk17 AS build
WORKDIR /workspace
COPY . .
RUN ./gradlew bootJar --no-daemon

FROM eclipse-temurin:17-jre-jammy
WORKDIR /app
COPY --from=build /workspace/build/libs/unryu-system.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","app.jar"]
