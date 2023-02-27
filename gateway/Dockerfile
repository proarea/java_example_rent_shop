FROM maven:3.8.4-jdk-17 as build
COPY . /app
COPY ./pom.xml /app

WORKDIR /app
RUN mvn -f pom.xml clean install -DskipTests

FROM maven:3.8.4-jdk-11
COPY --from=build /app/gateway/target/*.jar gateway-1.0-SNAPSHOT.jar
ENTRYPOINT [ "java", "-jar", "gateway-1.0-SNAPSHOT.jar"]