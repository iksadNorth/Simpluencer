package com.iksad.simpluencer.config;

import com.iksad.simpluencer.Properties.OAuth2ClientProperties;
import com.iksad.simpluencer.Properties.ServerProperties;
import com.iksad.simpluencer.model.ClientRegistration;
import com.iksad.simpluencer.repository.ClientRegistrationRepository;
import com.iksad.simpluencer.repository.InMemoryClientRegistrationRepository;
import com.iksad.simpluencer.type.OAuth2ProviderType;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

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
