package com.iksad.simpluencer.OAuth2;

import com.iksad.simpluencer.model.ClientRegistration;
import com.iksad.simpluencer.model.response.OAuth2TokenResponse;
import com.iksad.simpluencer.type.OAuth2ProviderType;
import org.springframework.stereotype.Component;

@Component
public class OAuth2AccessTokenRepositoryImpl implements OAuth2AccessTokenRepository {
    @Override
    public OAuth2TokenResponse getByCode(String code, ClientRegistration clientRegistration, Args args) {
        OAuth2ProviderType type = OAuth2ProviderType.providerOf(clientRegistration.getRegistrationId());
        return type.getOAuth2AccessTokenRepository().getByCode(code, clientRegistration, args);
    }

    @Override
    public OAuth2TokenResponse getByRefreshToken(String refreshToken, ClientRegistration clientRegistration, Args args) {
        OAuth2ProviderType type = OAuth2ProviderType.providerOf(clientRegistration.getRegistrationId());
        return type.getOAuth2AccessTokenRepository().getByRefreshToken(refreshToken, clientRegistration, args);
    }
}
