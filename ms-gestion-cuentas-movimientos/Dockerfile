# Etapa 1: Construir la aplicación
FROM maven:3.9.6-eclipse-temurin-21 AS builder
WORKDIR /app
COPY . .
RUN mvn clean package spring-boot:repackage -DskipTests -pl ms-gestion-cuentas-movimientos -am

# Etapa 2: Crear la imagen final
FROM eclipse-temurin:21-jdk
WORKDIR /app
COPY --from=builder /app/ms-gestion-cuentas-movimientos/target/*.jar app.jar
EXPOSE 8081
ENTRYPOINT ["java", "-jar", "app.jar"]