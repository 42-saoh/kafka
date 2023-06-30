FROM adoptopenjdk/openjdk11

COPY ./src/backend3.jar /usr/local/backend3.jar
RUN chmod +x /usr/local/backend3.jar
WORKDIR /usr/local

ENTRYPOINT ["java", "-jar", "backend3.jar"]