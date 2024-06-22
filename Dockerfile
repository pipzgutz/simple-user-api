FROM openjdk:21
VOLUME /tmp
ADD ./target/simple-user-api-1.0-SNAPSHOT.jar /app/simple-user-api-1.0-SNAPSHOT.jar
EXPOSE 8080/tcp
ENTRYPOINT ["java", "-Djava.security.egd=file:/dev/./urandom", "-jar", "/app/simple-user-api-1.0-SNAPSHOT.jar"]
