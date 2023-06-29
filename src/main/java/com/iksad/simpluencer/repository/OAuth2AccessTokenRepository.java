package com.iksad.simpluencer.repository;

import com.iksad.simpluencer.model.ClientRegistration;
import com.iksad.simpluencer.model.response.OAuth2TokenResponse;

public interface OAuth2AccessTokenRepository {
    OAuth2TokenResponse getAccessTokenByCode(ClientRegistration clientRegistration, String code);
    OAuth2TokenResponse getAccessTokenByRefreshCode(ClientRegistration clientRegistration, String refreshToken);
}
