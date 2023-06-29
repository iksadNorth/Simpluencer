package com.iksad.simpluencer.model.response;

import com.iksad.simpluencer.entity.AccessToken;
import com.iksad.simpluencer.entity.RefreshToken;
import com.iksad.simpluencer.model.ClientRegistration;
import lombok.Builder;

import java.time.Duration;
import java.time.Instant;

@Builder(toBuilder = true)
public record OAuth2AuthenticatedResponse(
        ClientRegistration clientRegistration,
        OAuth2TokenResponse tokenResponse,
        OAuth2UserInfoResponse userInfoResponse
) {
    public AccessToken toAccessTokenEntity() {
        ClientRegistration clientRegistration = this.clientRegistration();
        OAuth2TokenResponse tokenResponse = this.tokenResponse();
        OAuth2UserInfoResponse userInfoResponse = this.userInfoResponse();

        Instant now = Instant.now();

        AccessToken entity = new AccessToken();
        entity.setProvider(clientRegistration.getRegistrationId());
        entity.setPrincipalName(userInfoResponse.principalName());

        entity.setAccessToken(tokenResponse.accessToken());
        entity.setIssuedAtAccessToken(now);
        entity.setExpiresAtAccessToken(now.plus(Duration.of(tokenResponse.expiresIn(), OAuth2TokenResponse.TIME_UNIT)));

        return entity;
    }

    public RefreshToken toRefreshTokenEntity() {
        ClientRegistration clientRegistration = this.clientRegistration();
        OAuth2TokenResponse tokenResponse = this.tokenResponse();
        OAuth2UserInfoResponse userInfoResponse = this.userInfoResponse();

        Instant now = Instant.now();

        RefreshToken entity = new RefreshToken();
        entity.setProvider(clientRegistration.getRegistrationId());
        entity.setPrincipalName(userInfoResponse.principalName());

        entity.setRefreshToken(tokenResponse.refreshToken());
        entity.setIssuedAtRefreshToken(now);
        entity.setExpiresAtRefreshToken(null);

        return entity;
    }
}
