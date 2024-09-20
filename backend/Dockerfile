FROM eclipse-temurin:21-jdk-alpine
WORKDIR /app

# Copier les fichiers de configuration Maven
COPY .mvn/ .mvn
COPY mvnw pom.xml ./

# Télécharger les dépendances
RUN ./mvnw dependency:go-offline

# Copier le code source
COPY src ./src

# Compiler et exécuter l'application
CMD ["./mvnw", "spring-boot:run"]