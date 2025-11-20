
FROM maven:3.9.4-eclipse-temurin-17 AS build
WORKDIR /app

COPY pom.xml .
RUN mvn dependency:go-offline -B

COPY src ./src

RUN mvn clean package -DskipTests

FROM eclipse-temurin:17-jre   # Usamos JRE, más liviano que JDK
WORKDIR /app

COPY --from=build /app/target/*.jar app.jar

EXPOSE 8080   # Cambia si tu app usa otro puerto

ENTRYPOINT ["java", "-jar", "app.jar"]
