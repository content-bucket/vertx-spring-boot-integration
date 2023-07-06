FROM amazoncorretto:17 AS builder
WORKDIR /app
COPY .mvn .mvn
COPY mvnw mvnw
COPY pom.xml pom.xml
RUN ./mvnw verify --fail-never
COPY src src
RUN ./mvnw clean install

FROM amazoncorretto:17
WORKDIR /app
COPY --from=builder /app/target/vertx-vanilla-1.0.0-SNAPSHOT-fat.jar app.jar
CMD java -jar app.jar
