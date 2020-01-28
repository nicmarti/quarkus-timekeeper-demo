####
# This Dockerfile is used in order to build a container that runs the Quarkus application in JVM mode
#
# Before building the docker image run:
#
# mvn package
#
# Then, build the image with:
#
# docker build -f src/main/docker/Dockerfile.jvm -t quarkus/timekeeper-jvm .
#
# Then run the container using:
#
# docker run -i --rm -p 8080:8080 quarkus/timekeeper-jvm
#
###
FROM fabric8/java-alpine-openjdk8-jre:1.6.5
# jdbc:postgresql://my-postgresql-6132005/timekeeper
ENV JAVA_OPTIONS="-Dquarkus.http.host=0.0.0.0 -Djava.util.logging.manager=org.jboss.logmanager.LogManager -Dquarkus.datasource.url=jdbc:${QOVERY_DATABASE_MY_POSTGRESQL_6132005_URI}"
ENV AB_ENABLED=jmx_exporter

# Be prepared for running in OpenShift too
RUN adduser -G root --no-create-home --disabled-password 1001 \
  && chown -R 1001 /deployments \
  && chmod -R "g+rwX" /deployments \
  && chown -R 1001:root /deployments

COPY target/lib/* /deployments/lib/
COPY target/*-runner.jar /deployments/app.jar
EXPOSE 8080

# run with user 1001
USER 1001

ENTRYPOINT [ "/deployments/run-java.sh" ]
