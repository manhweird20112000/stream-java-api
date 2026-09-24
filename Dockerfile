FROM maven:3.9.9-eclipse-temurin-17 AS build

WORKDIR /app
COPY pom.xml .
COPY src ./src
RUN mvn -B test package

FROM eclipse-temurin:17-jre

WORKDIR /app
RUN groupadd --system app && useradd --system --gid app --home-dir /app app
COPY --chown=app:app --from=build /app/target/app.jar app.jar
USER app

EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]
