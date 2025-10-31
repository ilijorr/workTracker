# Build stage
FROM maven:3.9.6-eclipse-temurin-17 AS build
WORKDIR /.
COPY pom.xml .
COPY src ./src
RUN mvn clean package -DskipTests

# Runtime stage
FROM openjdk:17-jdk-slim
WORKDIR /.
COPY --from=build ./target/*.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]
