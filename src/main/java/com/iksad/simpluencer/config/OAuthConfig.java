package com.iksad.simpluencer.config;

import com.iksad.simpluencer.Properties.OAuth2ClientProperties;
import com.iksad.simpluencer.repository.ClientRegistrationRepository;
import com.iksad.simpluencer.repository.InMemoryClientRegistrationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class OAuthConfig {
    private final OAuth2ClientProperties oAuth2ClientProperties;

    @Bean
    public ClientRegistrationRepository clientRegistrationRepository() {
        return new InMemoryClientRegistrationRepository(oAuth2ClientProperties);
    }


}
