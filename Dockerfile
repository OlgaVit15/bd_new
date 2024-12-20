FROM openjdk:20
RUN mkdir /opt/app
ARG JAR_FILE=target/*.jar
COPY ./target/spring-boot-docker-1.0-SNAPSHOT.jar /target/app.jar
EXPOSE 8080
CMD ["java", "-jar", "./target/app.jar"]





