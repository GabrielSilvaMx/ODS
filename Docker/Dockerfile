# Alpine Linux with OpenJDK JRE
FROM bellsoft/liberica-openjdk-alpine:17
# Copy war file
COPY ODS-0.1.0.jar /ODS-0.1.0.war

ARG AZ_DATABASE_NAME=hsbc-rg-14633
ARG AZ_MYSQL_NON_ADMIN_USERNAME=nhdiviou
ARG AZ_MYSQL_NON_ADMIN_PASSWORD=Bedu2023

# run the app
CMD ["/usr/lib/jvm/jdk/bin/java", "-jar", "/ODS-0.1.0.war"]