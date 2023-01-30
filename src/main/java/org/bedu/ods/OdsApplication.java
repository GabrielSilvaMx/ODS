package org.bedu.ods;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;

@SpringBootApplication
@ConfigurationPropertiesScan
public class OdsApplication {

    public static void main(String[] args) {
        SpringApplication.run(OdsApplication.class, args);
    }

}
