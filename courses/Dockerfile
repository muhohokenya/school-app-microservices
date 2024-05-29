FROM openjdk:17-jdk-slim

# Set the working directory to /app
WORKDIR /app

# Install wget and unzip (needed to download and extract Gradle)
RUN apt-get update && \
    apt-get install -y wget unzip

# Copy the Gradle wrapper files and build.gradle.kts
COPY gradlew .
COPY gradle gradle
COPY build.gradle.kts .

# Copy the source code
COPY src src

# Download dependencies and build the application
#RUN ./gradlew build

# Expose the port the app runs on
EXPOSE 8081

COPY build/libs/courses-0.0.1-SNAPSHOT.jar /app/courses.jar

# Run the application
CMD ["java", "-jar", "courses.jar"]
