package org.bedu.ods.Config.jwt;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "jwt")
@Data
public class JwtProperties {
    private String secretKey = "rzxlszyykpbgqcflzxsqcysyhljt";

    // validación en milisegundos
    private long validityInMs = 3600000; // 1h
}
