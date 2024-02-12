FROM openjdk:17
VOLUME /tmp
EXPOSE 8080
ARG JAR_FILE=target/minibanco-0.0.1-SNAPSHOT.jar
ADD ${JAR_FILE} appMiniBanco.jar
ENTRYPOINT ["java","-jar","/appMiniBanco.jar"]