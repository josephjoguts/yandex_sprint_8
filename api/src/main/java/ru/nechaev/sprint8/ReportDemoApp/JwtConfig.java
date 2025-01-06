package ru.nechaev.sprint8.ReportDemoApp;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.core.OAuth2Error;
import org.springframework.security.oauth2.core.OAuth2TokenValidatorResult;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtDecoders;
import org.springframework.security.oauth2.jwt.JwtValidators;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;

import java.time.Instant;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Configuration
public class JwtConfig {
    @Value("${spring.security.oauth2.resourceserver.jwt.issuer-uri}")
    private String issuer;
    @Bean
    public JwtDecoder jwtDecoder() {
        String issuerUri = issuer;
        NimbusJwtDecoder jwtDecoder = (NimbusJwtDecoder) JwtDecoders.fromIssuerLocation(issuerUri);

        jwtDecoder.setClaimSetConverter(claims -> {
            Map<String, Object> mutableClaims = new HashMap<>(claims);
            if (claims.containsKey("exp")) {
                mutableClaims.put("exp", convertToInstant(claims.get("exp")));
            }
            if (claims.containsKey("iat")) {
                mutableClaims.put("iat", convertToInstant(claims.get("iat")));
            }
            if (claims.containsKey("auth_time")) {
                mutableClaims.put("auth_time", convertToInstant(claims.get("auth_time")));
            }
            if (claims.containsKey("iss")) {
                mutableClaims.put("iss", issuerUri);
            }

            return mutableClaims;
        });

        return jwtDecoder;
    }

    private Instant convertToInstant(Object timestamp) {
        if (timestamp instanceof Date) {
            return ((Date) timestamp).toInstant();
        } else if (timestamp instanceof Long) {
            return Instant.ofEpochSecond((Long) timestamp);
        } else if (timestamp instanceof Instant) {
            return (Instant) timestamp;
        } else {
            throw new IllegalArgumentException("Unsupported timestamp type: " + timestamp.getClass());
        }
    }
}
