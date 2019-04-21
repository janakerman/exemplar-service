FROM openjdk:8-jre-alpine

USER root

RUN adduser -D -u 1000 app

USER app

COPY build/libs/exemplar-service-0.0.1-SNAPSHOT.jar /home/app/exemplar-service.jar

WORKDIR /home/app

EXPOSE 8080

CMD java -jar exemplar-service.jar
