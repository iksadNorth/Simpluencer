package com.iksad.simpluencer.Properties;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

@Getter
@Configuration
@ConfigurationProperties("spring.security.oauth2.client")
public class OAuth2ClientProperties {
    private final Map<String, Registration> registration = new HashMap<>();

    @Builder(toBuilder = true)
    @Getter @Setter
    public static class Registration {
        private String clientId;
        private String clientSecret;
    }
}
