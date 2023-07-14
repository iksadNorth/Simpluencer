FROM openjdk:17-jdk-slim
LABEL authors="iksadnorth"

EXPOSE 8080

COPY /build/libs/simpluencer-0.0.1-SNAPSHOT.jar app.jar
COPY /src/main/resources/static/img/ /src/img/

ENTRYPOINT ["java", "-jar", "app.jar"]
CMD ["--spring.profiles.active=prod"]
