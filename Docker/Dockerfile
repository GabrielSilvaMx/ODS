ARG LIBERICA_VERSION=17.0.6

# Alpine Linux with OpenJDK JRE
FROM bellsoft/liberica-openjdk-alpine:${LIBERICA_VERSION}

# Copy war file
COPY ODS-0.1.0.jar /ODS-0.1.0.war

ENV AZ_DATABASE_NAME=database.mysql.database.azure.com
ENV AZ_MYSQL_NON_ADMIN_USERNAME=username
ENV AZ_MYSQL_NON_ADMIN_PASSWORD=password

# run the app
CMD ["/usr/lib/jvm/jdk/bin/java", "-jar", "/ODS-0.1.0.war"]