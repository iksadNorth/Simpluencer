package com.iksad.simpluencer.Properties;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

@Configuration
@ConfigurationProperties("spring.security.oauth2.client")
public class OAuth2ClientProperties implements InitializingBean {
    private final Map<String, Provider> provider = new HashMap<>();

    private final Map<String, Registration> registration = new HashMap<>();

    public Map<String, Provider> getProvider() {
        return this.provider;
    }

    public Map<String, Registration> getRegistration() {
        return this.registration;
    }

    @Override
    public void afterPropertiesSet() {
        validate();
    }

    public void validate() {
        getRegistration().values().forEach(this::validateRegistration);
    }

    private void validateRegistration(Registration registration) {
        if (!StringUtils.hasText(registration.getClientId())) {
            throw new IllegalStateException("Client id must not be empty.");
        }
    }

    @Builder(toBuilder = true)
    @Getter @Setter
    public static class Registration {
        private String provider;
        private String clientId;
        private String clientSecret;
        private String clientAuthenticationMethod;
        private String authorizationGrantType;
        private String redirectUri;
        private Set<String> scope;
        private String clientName;
    }

    @Builder(toBuilder = true)
    @Getter @Setter
    public static class Provider {
        private String authorizationUri;
        private String tokenUri;
        private String userInfoUri;
        private String userInfoAuthenticationMethod;
        private String userNameAttribute;
        private String jwkSetUri;
        private String issuerUri;
    }
}
