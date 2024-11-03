FROM openjdk:17-jdk-alpine

# Copy the JAR file into the container
ARG JAR_FILE=target/auth-0.0.1-SNAPSHOT.jar
COPY ${JAR_FILE} app.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","/app.jar"]
