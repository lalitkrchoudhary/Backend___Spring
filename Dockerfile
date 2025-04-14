# Stage 1: Build the application using Maven
FROM maven:3.9.5-eclipse-temurin-17 AS build

WORKDIR /app

# Copy pom.xml and download dependencies (for layer caching)
COPY pom.xml .
RUN mvn dependency:go-offline

# Copy the rest of the source code
COPY src ./src

# Package the Spring Boot app
RUN mvn clean package -DskipTests

# Stage 2: Run the application using a minimal JDK image
FROM eclipse-temurin:17-jdk-alpine

WORKDIR /app

# Copy the jar file from the build stage
COPY --from=build /app/target/RestApiProject-0.0.1-SNAPSHOT.jar .

# Expose the port (update if your app runs on a different port)
EXPOSE 8080

# Run the application
ENTRYPOINT ["java", "-jar", "RestApiProject-0.0.1-SNAPSHOT.jar"]
