FROM icr.io/appcafe/open-liberty:full-java17-openj9-ubi as staging

COPY --chown=1001:0 /mnt/e/BEDU/ODS/target/thin-ODS-0.1.0.jar \
                    /staging/fat-ODS-0.1.0.jar

RUN springBootUtility thin \
 --sourceAppPath=/staging/fat--ODS-0.1.0.jar \
 --targetThinAppPath=/staging/thin-ODS-0.1.0.jar \
 --targetLibCachePath=/staging/lib.index.cache

FROM icr.io/appcafe/open-liberty:full-java17-openj9-ubi

ARG VERSION=1.0
ARG REVISION=SNAPSHOT

LABEL \
  org.opencontainers.image.authors="Gabriel Silva V." \
  org.opencontainers.image.vendor="Open Liberty" \
  org.opencontainers.image.url="local" \
  org.opencontainers.image.source="https://github.com/GabrielSilvaMx/ODS" \
  org.opencontainers.image.version="$VERSION" \
  org.opencontainers.image.revision="$REVISION" \
  vendor="Open Liberty" \
  name="ODS API" \
  version="$VERSION-$REVISION" \
  summary="The hello application from the Spring Boot guide" \
  description="This image contains the hello application running with the Open Liberty runtime."

RUN cp /mnt/e/BEDU/ODS/src/main/liberty/config/server.xml /config/server.xml

COPY --chown=1001:0 --from=staging /staging/lib.index.cache /lib.index.cache
COPY --chown=1001:0 --from=staging /staging/thin-guide-spring-boot-0.1.0.jar \
                    /config/dropins/spring/thin-guide-spring-boot-0.1.0.jar

RUN configure.sh