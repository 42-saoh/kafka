FROM adoptopenjdk/openjdk11

COPY ./src/backend1.jar /usr/local/backend1.jar
RUN chmod +x /usr/local/backend1.jar
WORKDIR /usr/local

ENTRYPOINT ["java", "-jar", "backend1.jar"]