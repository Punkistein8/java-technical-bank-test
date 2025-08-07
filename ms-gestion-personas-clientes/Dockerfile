# Etapa 1: Build
FROM maven:3.9.6-eclipse-temurin-21 AS build
WORKDIR /build

# Copiar todo el proyecto (nivel raíz del multi-módulo)
COPY ../.. .

# Construir solo el módulo deseado y sus dependencias
RUN mvn clean package -pl ms-gestion-personas-clientes -am -DskipTests

# Etapa 2: Imagen final
FROM eclipse-temurin:21-jdk
WORKDIR /app

# Copiar el jar reempaquetado
COPY --from=build /build/ms-gestion-personas-clientes/target/ms-gestion-personas-clientes-*.jar app.jar

EXPOSE 8081
ENTRYPOINT ["java", "-jar", "app.jar"]
