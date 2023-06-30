FROM adoptopenjdk/openjdk11

COPY ./src/backend2.jar /usr/local/backend2.jar
RUN chmod +x /usr/local/backend2.jar
WORKDIR /usr/local

ENTRYPOINT ["java", "-jar", "backend2.jar"]