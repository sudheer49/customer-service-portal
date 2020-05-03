FROM java:8-jdk-alpine
COPY ./target/customer-service-0.0.1-SNAPSHOT.jar /usr/app/
WORKDIR /usr/app
EXPOSE 8082
ENTRYPOINT ["java","-jar","customer-service-0.0.1-SNAPSHOT.jar"]