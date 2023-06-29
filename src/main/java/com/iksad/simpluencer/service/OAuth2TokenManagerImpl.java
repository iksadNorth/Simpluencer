package com.iksad.simpluencer.service;

import com.iksad.simpluencer.entity.AccessToken;
import com.iksad.simpluencer.entity.RefreshToken;
import com.iksad.simpluencer.exception.ErrorType.RefreshTokenNotFoundType;
import com.iksad.simpluencer.model.ClientRegistration;
import com.iksad.simpluencer.model.response.OAuth2TokenResponse;
import com.iksad.simpluencer.repository.AccessTokenRepository;
import com.iksad.simpluencer.repository.ClientRegistrationRepository;
import com.iksad.simpluencer.repository.OAuth2AccessTokenRepository;
import com.iksad.simpluencer.repository.RefreshTokenRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class OAuth2TokenManagerImpl implements OAuth2TokenManager {
    private final ClientRegistrationRepository clientRegistrationRepository;
    private final OAuth2AccessTokenRepository oAuth2AccessTokenRepository;
    private final AccessTokenRepository accessTokenRepository;
    private final RefreshTokenRepository refreshTokenRepository;

    @Override
    public String getAccessToken(String providerName, String principalName) {
        ClientRegistration clientRegistration = clientRegistrationRepository.findByRegistrationId(providerName);

        // access token을 로드.
        Optional<AccessToken> optionalAccessToken = accessTokenRepository.findByProviderAndPrincipalName(providerName, principalName);

        // access token의 유효성 확인.
        // 유효하면 그대로 출력.
        // 유효하지 않으면 refresh token으로 access token 로드 후 출력.
        return optionalAccessToken.filter(OAuth2TokenManagerImpl::isAccessTokenValid)
                .map(AccessToken::getAccessToken)
                .orElseGet(() -> this.getAccessTokenWhenAccessTokenNotExist(clientRegistration, providerName, principalName));
    }

    private String getAccessTokenWhenAccessTokenNotExist(ClientRegistration clientRegistration, String providerName, String principalName) {
        accessTokenRepository.deleteByProviderAndPrincipalName(providerName, principalName);

        return this.getAccessTokenByRefreshToken(
                clientRegistration,
                this.loadRefreshTokenByProviderAndPrincipalName(providerName, principalName)
        );
    }

    private String loadRefreshTokenByProviderAndPrincipalName(String providerName, String principalName) {
        return refreshTokenRepository.findByProviderAndPrincipalName(providerName, principalName)
                .map(RefreshToken::getRefreshToken)
                .orElseThrow(RefreshTokenNotFoundType::new);
    }

    private String getAccessTokenByRefreshToken(ClientRegistration clientRegistration, String refreshToken) {
        OAuth2TokenResponse tokenResponse = oAuth2AccessTokenRepository.getAccessTokenByRefreshCode(clientRegistration, refreshToken);
        return tokenResponse.accessToken();
    }

    private static boolean isAccessTokenValid(AccessToken token) {
        if (token == null) {
            return false;
        }
        Instant now = Instant.now();
        return token.getExpiresAtAccessToken().isAfter(now);
    }
}
