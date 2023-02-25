package org.bedu.ods.config.jwt;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "jwt")
@Data

public class JwtProperties {

    @Value("${jwt.secretkey}")
    private String secretKey;

    // validación en milisegundos
    private long validityInMs = 3600000; // 1h
}
