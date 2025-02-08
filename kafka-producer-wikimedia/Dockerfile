# Use an official OpenJDK runtime as a parent image
FROM openjdk:17-jdk-alpine
# Set the working directory inside the container
WORKDIR /app
# Copy the built JAR file into the container
COPY kafka-producer-wikimedia/target/kafka-producer-wikimedia-0.0.1-SNAPSHOT.jar /app
# Expose the port the app runs on
EXPOSE 8080
# Command to run the application
ENTRYPOINT ["java", "-jar", "kafka-producer-wikimedia-0.0.1-SNAPSHOT.jar"]