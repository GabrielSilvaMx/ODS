FROM icr.io/appcafe/open-liberty:full-java17-openj9-ubi as staging

<<<<<<< HEAD
COPY --chown=1001:0 target/ODS-0.1.0.jar \
                    /staging/fat-ODS-0.1.0.jar

RUN springBootUtility thin \
 --sourceAppPath=/staging/fat-ODS-0.1.0.jar \
=======
COPY --chown=1001:0 /mnt/e/BEDU/ODS/target/thin-ODS-0.1.0.jar \
                    /staging/fat-ODS-0.1.0.jar

RUN springBootUtility thin \
 --sourceAppPath=/staging/fat--ODS-0.1.0.jar \
>>>>>>> origin/developer_house
 --targetThinAppPath=/staging/thin-ODS-0.1.0.jar \
 --targetLibCachePath=/staging/lib.index.cache

FROM icr.io/appcafe/open-liberty:full-java17-openj9-ubi

ARG VERSION=1.0
ARG REVISION=SNAPSHOT

LABEL \
<<<<<<< HEAD
  org.opencontainers.image.authors="Gabriel Silva Valencia" \
=======
  org.opencontainers.image.authors="Gabriel Silva V." \
>>>>>>> origin/developer_house
  org.opencontainers.image.vendor="Open Liberty" \
  org.opencontainers.image.url="local" \
  org.opencontainers.image.source="https://github.com/GabrielSilvaMx/ODS" \
  org.opencontainers.image.version="$VERSION" \
  org.opencontainers.image.revision="$REVISION" \
  vendor="Open Liberty" \
<<<<<<< HEAD
  name="KANBAN ODS API" \
  version="$VERSION-$REVISION" \
  summary="Tablero Kanban para proyectos de ODS" \
  description="Este repositorio es para la entrega del proyecto para HSBC."

COPY --chown=1001:0 --from=staging /staging/lib.index.cache /lib.index.cache
COPY --chown=1001:0 --from=staging /staging/thin-ODS-0.1.0.jar \
                    /config/dropins/spring/thin-ODS-0.1.0.jar
=======
  name="ODS API" \
  version="$VERSION-$REVISION" \
  summary="The hello application from the Spring Boot guide" \
  description="This image contains the hello application running with the Open Liberty runtime."

RUN cp /mnt/e/BEDU/ODS/src/main/liberty/config/server.xml /config/server.xml

COPY --chown=1001:0 --from=staging /staging/lib.index.cache /lib.index.cache
COPY --chown=1001:0 --from=staging /staging/thin-guide-spring-boot-0.1.0.jar \
                    /config/dropins/spring/thin-guide-spring-boot-0.1.0.jar
>>>>>>> origin/developer_house

RUN configure.sh