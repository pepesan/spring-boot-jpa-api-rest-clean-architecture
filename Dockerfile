# Build stage
FROM eclipse-temurin:21-jdk AS builder
WORKDIR /app
COPY mvnw .
COPY .mvn .mvn
COPY pom.xml .
RUN ./mvnw dependency:go-offline -B
COPY src ./src
RUN ./mvnw package -DskipTests -B

# Run stage
FROM eclipse-temurin:21-jre
WORKDIR /app
COPY --from=builder /app/target/*.jar app.jar

# OpenShift: random UID with GID 0 — grant group write access
RUN chgrp -R 0 /app && chmod -R g=u /app

EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]
