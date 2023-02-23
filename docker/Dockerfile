ARG LIBERICA_VERSION=17.0.6

# Alpine Linux with OpenJDK JRE
FROM bellsoft/liberica-openjdk-alpine:${LIBERICA_VERSION}

# Copy war file
COPY ODS-0.1.0.jar /ODS-0.1.0.war

# run the app
CMD ["/usr/lib/jvm/jdk/bin/java", "-jar", "/ODS-0.1.0.war"]