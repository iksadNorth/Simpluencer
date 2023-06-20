package com.iksad.simpluencer.config;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.oauth2.client.OAuth2ClientProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.oauth2.client.registration.InMemoryClientRegistrationRepository;
import org.springframework.security.oauth2.core.AuthorizationGrantType;

import java.util.List;
import java.util.Map;

@Configuration
public class OAuthConfig {
    private final Map<String, OAuth2ClientProperties.Registration> registrations;
    private final Map<String, OAuth2ClientProperties.Provider> providers;

    @Autowired
    public OAuthConfig(OAuth2ClientProperties oAuth2ClientProperties) {
        this.registrations = oAuth2ClientProperties.getRegistration();
        this.providers = oAuth2ClientProperties.getProvider();
    }

    @Bean
    public ClientRegistrationRepository clientRegistrationRepository() {
        List<ClientRegistration> clientRegistrations = registrations.keySet().stream()
                .map(this::castToClientRegistration)
                .toList();
        return new InMemoryClientRegistrationRepository(clientRegistrations);
    }

    private ClientRegistration castToClientRegistration(String registrationId) {
        OAuth2ClientProperties.Registration registration = registrations.get(registrationId);
        OAuth2ClientProperties.Provider provider = providers.get(registrationId);

        return ClientRegistration.withRegistrationId(registrationId)

                .authorizationGrantType(AuthorizationGrantType.AUTHORIZATION_CODE)
                .clientId(registration.getClientId())
                .clientSecret(registration.getClientSecret())
                .redirectUri(registration.getRedirectUri())
                .scope(registration.getScope())

                .authorizationUri(provider.getAuthorizationUri())
                .tokenUri(provider.getTokenUri())
                .userNameAttributeName(provider.getUserNameAttribute())

                .build();
    }
}
