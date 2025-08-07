# Etapa 1: Construcción de la app
FROM maven:3.9.6-eclipse-temurin-21 AS build
WORKDIR /build

# Copiar todo el proyecto (raíz del multi-módulo)
COPY ../.. .

# Construir solo este microservicio y sus dependencias
RUN mvn clean package -pl ms-gestion-cuentas-movimientos -am -DskipTests

# Etapa 2: Imagen final
FROM eclipse-temurin:21-jdk
WORKDIR /app

# Copiar el jar ejecutable desde la etapa anterior
COPY --from=build /build/ms-gestion-cuentas-movimientos/target/ms-gestion-cuentas-movimientos-*-SNAPSHOT.jar app.jar

EXPOSE 8081
ENTRYPOINT ["java", "-jar", "app.jar"]
