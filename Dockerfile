# ========================================
# ETAPA 1: BUILD (Compilación)
# ========================================
# Usamos eclipse-temurin que es la imagen recomendada actualmente (openjdk está deprecada)
FROM eclipse-temurin:17-jdk-alpine as build

WORKDIR /app

# Copiar todo el código
COPY . .

# Dar permisos de ejecución al script gradlew y arreglar finales de línea (CRLF) de Windows
RUN chmod +x ./gradlew && sed -i 's/\r$//' gradlew

# Ejecutar Gradle para compilar
RUN ./gradlew bootJar --no-daemon

# ========================================
# ETAPA 2: RUNTIME (Ejecución)
# ========================================
# Usamos la versión JRE (más ligera) para correr la app
FROM eclipse-temurin:17-jre-alpine

WORKDIR /app

# Documentar puerto
EXPOSE 8080

# Copiar el JAR generado en la etapa anterior
COPY --from=build /app/build/libs/Mutantes-1.0-SNAPSHOT.jar ./app.jar

ENTRYPOINT ["java", "-jar", "app.jar"]