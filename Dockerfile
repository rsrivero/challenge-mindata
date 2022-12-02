FROM maven:3.8-openjdk-11-slim AS build
COPY src /usr/src/app/src
COPY pom.xml /usr/src/app
RUN mvn -f /usr/src/app/pom.xml clean package

FROM adoptopenjdk/openjdk11:alpine-jre
COPY --from=build /usr/src/app/target/challenge-mindata-0.0.1.jar /usr/app/challenge-mindata-0.0.1.jar
EXPOSE 8080:8080
ENTRYPOINT ["java","-jar","/usr/app/challenge-mindata-0.0.1.jar"]