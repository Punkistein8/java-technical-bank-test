# Etapa 1: Construir la aplicación
FROM maven:3.8.5-openjdk-17 AS builder
WORKDIR /app
COPY ../../../.m2/repository /root/.m2/repository
COPY pom.xml .
COPY src ./src
RUN mvn clean package -DskipTests

# Etapa 2: Crear la imagen final
FROM openjdk:17-jdk-slim
WORKDIR /app
COPY --from=builder /app/target/ms-core-persona-cliente-0.0.1-SNAPSHOT.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]