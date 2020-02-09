FROM openjdk:8

ADD  target/simuladao-0.0.1-SNAPSHOT.jar app.jar

ENTRYPOINT ["java","-jar","app.jar]

EXPOSE 8080