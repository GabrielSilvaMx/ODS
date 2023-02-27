ARG LIBERICA_VERSION=17.0.6

# Alpine Linux with OpenJDK JRE
FROM bellsoft/liberica-openjdk-alpine:${LIBERICA_VERSION}

# Copy war file
COPY ODS-0.1.0.jar /ODS-0.1.0.war

ENV AZ_DATABASE_NAME=database.mysql.com
ENV AZ_MYSQL_NON_ADMIN_USERNAME=username
ENV AZ_MYSQL_NON_ADMIN_PASSWORD=password
ENV JWT_SECRET_KEY=fdkj8unfdfjfds908fidkfdpop
ENV LOCAL_DATABASE_NAME=localhost
ENV LOCAL_MYSQL_NON_ADMIN_USERNAME=username
ENV LOCAL_MYSQL_NON_ADMIN_PASSWORD=password

# run the app
CMD ["/usr/lib/jvm/jdk/bin/java", "-jar", "/ODS-0.1.0.war"]