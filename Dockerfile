FROM openjdk:17-jdk-alpine
WORKDIR /app
COPY ./target/scheduler-0.0.1-SNAPSHOT.jar /app
EXPOSE 3001
ENTRYPOINT [ "java", "-jar", "scheduler-0.0.1-SNAPSHOT.jar" ]
