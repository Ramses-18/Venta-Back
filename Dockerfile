FROM eclipse-temurin:17-jdk-jammy

WORKDIR /app

# Copiar pom y descargar dependencias (cache layer)
COPY pom.xml .
RUN apt-get update && apt-get install -y maven && mvn dependency:go-offline -B

# Copiar codigo fuente y compilar
COPY src ./src
RUN mvn clean package -DskipTests -B

EXPOSE 8080

CMD ["java", "-jar", "target/lulu-app-1.0.0.jar"]