# ===== Stage 1: Build =====
FROM maven:3.9.6-eclipse-temurin-21 AS build
WORKDIR /app

# Copia pom e resolve dependências em cache
COPY pom.xml .
RUN mvn -q -DskipTests dependency:go-offline

# Copia código e compila
COPY src ./src
RUN mvn -q -DskipTests clean package

# ===== Stage 2: Runtime =====
FROM eclipse-temurin:21-jre
WORKDIR /app

# Perfil padrão e porta
ENV SPRING_PROFILES_ACTIVE=prod
EXPOSE 8080

# Copia o JAR do estágio de build
COPY --from=build /app/target/forumhub-0.0.1-SNAPSHOT.jar app.jar

# Inicia a aplicação
ENTRYPOINT ["java","-jar","/app/app.jar"]
