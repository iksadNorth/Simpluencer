package com.iksad.simpluencer.OAuth2;

import com.iksad.simpluencer.Properties.ServerProperties;
import com.iksad.simpluencer.model.ClientRegistration;
import com.iksad.simpluencer.model.response.OAuth2TokenResponse;
import lombok.Builder;
import org.springframework.web.client.RestTemplate;

public interface OAuth2AccessTokenRepository {
    @Builder
    public record Args(
            ServerProperties serverProperties,
            RestTemplate restTemplate
    ) {}
    OAuth2TokenResponse getByCode(String code, ClientRegistration clientRegistration, Args args);
    OAuth2TokenResponse getByRefreshToken(String refreshToken, ClientRegistration clientRegistration, Args args);
}
