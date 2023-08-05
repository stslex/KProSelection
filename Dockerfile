FROM gradle:7-jdk11 AS build
LABEL authors="stslex"
COPY --chown=gradle:gradle . /home/gradle/src
WORKDIR /home/gradle/src
RUN gradle :test --tests "stslex.com.TestAppConfig"
RUN gradle buildFatJar --no-daemon
RUN rm -rf /home/gradle/src/src/main/resources/application.conf

FROM openjdk:11
EXPOSE 8080:8080
RUN mkdir /app
COPY --from=build /home/gradle/src/build/libs/*.jar /app/ktor-docker-sample.jar
ENTRYPOINT ["java","-jar","/app/ktor-docker-sample.jar"]