FROM maven:3.6.0-jdk-11-slim AS build

# Copy the source code
RUN rm -rf /usr/src/app/*
COPY src /usr/src/app/src
COPY pom.xml /usr/src/app
USER root
# Setup working directory
WORKDIR /usr/src/app

# Speed up Maven JVM a bit
ENV MAVEN_OPTS="-XX:+TieredCompilation -XX:TieredStopAtLevel=1"

# Compile the code, run unit tests and pack the fat-JAR file
RUN mvn -T 1C -f /usr/src/app/pom.xml clean package  -DskipTests


FROM java:8-jdk-alpine
COPY --from=build /usr/src/app/target/customer*.jar /home/app/app.jar
EXPOSE 8082
ENTRYPOINT java -jar /home/app/app.jar