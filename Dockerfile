####
# This is a Dockerfile to deploy this application to Qovery https://www.qovery.com/
#
# Execute this file with
# docker build -t quarkus/timekeeper-jvm -f Dockerfile .
#
# Quarkus 1.2.0 final is required - It is normally set in pom.xml
#########################################################################################################

## Stage 1 : build with maven builder image with native capabilities
# see https://github.com/quarkusio/quarkus-images
# see also https://quay.io/repository/quarkus/centos-quarkus-maven?tab=tags
# see doc https://quarkus.io/guides/building-native-image#creating-a-container-with-a-multi-stage-docker-build
# ---
FROM quay.io/quarkus/centos-quarkus-maven:19.2.1 AS build
COPY src /usr/src/app/src
COPY pom.xml /usr/src/app
USER root
RUN chown -R quarkus /usr/src/app
USER quarkus
EXPOSE 8080
RUN mvn -f /usr/src/app/pom.xml -DskipTests -Pnative clean package

## Stage 2 : create the docker final image
FROM registry.access.redhat.com/ubi8/ubi-minimal
WORKDIR /work/
COPY --from=build /usr/src/app/target/*-runner /work/application
RUN chmod 775 /work
EXPOSE 8080
ARG QOVERY_DATABASE_MY_POSTGRESQL_6132005_DATABASE
ARG QOVERY_DATABASE_MY_POSTGRESQL_6132005_USERNAME
ARG QOVERY_DATABASE_MY_POSTGRESQL_6132005_PASSWORD
ARG QOVERY_DATABASE_MY_POSTGRESQL_6132005_HOST
ARG QOVERY_DATABASE_MY_POSTGRESQL_6132005_PORT
LABEL version=1.2
LABEL description="Quarkus distro less timekeeper 1.2"
# We cannot injet an URI here with username:password@host as it is not supported by the JDBC driver.
# This is why we have to specify each argument
CMD ["./application", "-Dquarkus.http.host=0.0.0.0", "-Dquarkus.log.level=FINEST", "-Dquarkus.log.console.level=FINEST", "-Dquarkus.datasource.url=jdbc:postgresql://${QOVERY_DATABASE_MY_POSTGRESQL_6132005_HOST}:${QOVERY_DATABASE_MY_POSTGRESQL_6132005_PORT}/${QOVERY_DATABASE_MY_POSTGRESQL_6132005_DATABASE}", "-Dquarkus.datasource.username=${QOVERY_DATABASE_MY_POSTGRESQL_6132005_USERNAME}", "-Dquarkus.datasource.password=${QOVERY_DATABASE_MY_POSTGRESQL_6132005_PASSWORD}", "-Dquarkus.ssl.native=true"]
