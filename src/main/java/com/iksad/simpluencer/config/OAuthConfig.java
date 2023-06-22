package com.iksad.simpluencer.config;

import com.iksad.simpluencer.Properties.ServerProperties;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.security.oauth2.client.OAuth2ClientProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.oauth2.client.registration.InMemoryClientRegistrationRepository;

import java.util.List;
import java.util.Map;

@Configuration
@RequiredArgsConstructor
public class OAuthConfig {
    private final OAuth2ClientProperties oAuth2ClientProperties;
    private final ServerProperties serverProperties;

    private List<ClientRegistration> clientRegistrations(){
        return oAuth2ClientProperties.getRegistration().entrySet().stream()
                .map(this::castToClientRegistration)
                .toList();
    }

    @Bean
    public ClientRegistrationRepository clientRegistrationRepository() {
        return new InMemoryClientRegistrationRepository(clientRegistrations());
    }

    private ClientRegistration castToClientRegistration(Map.Entry<String, OAuth2ClientProperties.Registration> entry) {
        String registrationId = entry.getKey();
        OAuth2ProviderType provider = OAuth2ProviderType.valueOf(registrationId);
        OAuth2ClientProperties.Registration registration = entry.getValue();

        return provider.getBuilder(registrationId, serverProperties)
                .clientId(registration.getClientId())
                .clientSecret(registration.getClientSecret())
                .build();
    }
}
