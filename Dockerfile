FROM openjdk:17.0.1-jdk-slim

ENV POSTGRES_SERVER=db
ENV POSTGRES_PORT=5432

ENV POSTGRES_USER=postgres
ENV POSTGRES_PASSWORD=password

ENV POSTGRES_DB=postgres

WORKDIR /opt/app

EXPOSE 8080


ADD src ./src
ADD gradle/ ./gradle
COPY build.gradle ./build.gradle
COPY settings.gradle ./settings.gradle
COPY gradlew ./gradlew
COPY gradlew.bat ./gradlew.bat

RUN ./gradlew clean bootJar
ENTRYPOINT ["java","-jar", "./build/libs/yandex-lavka-0.0.1-SNAPSHOT.jar"]