FROM openjdk:17.0.1-jdk-slim
FROM gradle:latest

ENV POSTGRES_SERVER=db
ENV POSTGRES_PORT=5432
ENV POSTGRES_DB=postgres
ENV POSTGRES_USER=postgres
ENV POSTGRES_PASSWORD=password
ENV POSTGRES_DSN=postgresql://postgres:password@db/postgres

EXPOSE 8080

WORKDIR /opt/app

COPY . .
RUN gradle clean bootJar

ENTRYPOINT ["java","-jar", "./build/libs/yandex-lavka-0.0.1-SNAPSHOT.jar"]