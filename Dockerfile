####
# This is a Dockerfile to deploy this application to Qovery https://www.qovery.com/
#
# Execute this file with
# docker build -t quarkus/timekeeper-jvm -f Dockerfile .
#
# Quarkus 1.2.0 final is required - It is normally set in pom.xml
#########################################################################################################

## Stage 0 :
# We need SSL libraries for SSL support on the native image
#  see https://quarkus.io/guides/native-and-ssl
FROM quay.io/quarkus/ubi-quarkus-native-image:19.3.1-java8 as nativebuilder
RUN mkdir -p /tmp/ssl-libs/lib \
  && cp /opt/graalvm/jre/lib/security/cacerts /tmp/ssl-libs \
  && cp /opt/graalvm/jre/lib/amd64/libsunec.so /tmp/ssl-libs/lib/

## Stage 1 : build with maven builder image with native capabilities
# see https://github.com/quarkusio/quarkus-images
# see also https://quay.io/repository/quarkus/centos-quarkus-maven?tab=tags
# see doc https://quarkus.io/guides/building-native-image#creating-a-container-with-a-multi-stage-docker-build
# ---
FROM quay.io/quarkus/centos-quarkus-maven:19.3.1-java8 AS build
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
# For SSL - see https://quarkus.io/guides/native-and-ssl
COPY --from=nativebuilder /tmp/ssl-libs/ /work/
# And the binary
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
CMD ["./application", "-Dquarkus.http.host=0.0.0.0", "-Dquarkus.log.level=FINEST", "-Dquarkus.log.console.level=FINEST", "-Dquarkus.datasource.url=jdbc:postgresql://${QOVERY_DATABASE_MY_POSTGRESQL_6132005_HOST}:${QOVERY_DATABASE_MY_POSTGRESQL_6132005_PORT}/${QOVERY_DATABASE_MY_POSTGRESQL_6132005_DATABASE}", "-Dquarkus.datasource.username=${QOVERY_DATABASE_MY_POSTGRESQL_6132005_USERNAME}", "-Dquarkus.datasource.password=${QOVERY_DATABASE_MY_POSTGRESQL_6132005_PASSWORD}", "-Djava.library.path=/work/lib", "-Djavax.net.ssl.trustStore=/work/cacerts"]
