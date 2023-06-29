package com.iksad.simpluencer.model.response;

import lombok.Builder;

import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalUnit;
import java.util.Map;

@Builder(toBuilder = true)
public record OAuth2TokenResponse(
    String accessToken,
    String refreshToken,
    Long expiresIn
) {
    public static final TemporalUnit TIME_UNIT = ChronoUnit.SECONDS;

    public static OAuth2TokenResponse of(Map<String, String> entityBody) {
        return OAuth2TokenResponse.builder()
                .accessToken(entityBody.getOrDefault("access_token", null))
                .refreshToken(entityBody.getOrDefault("refresh_token", null))
                .expiresIn(Long.valueOf(entityBody.getOrDefault("expires_in", "0")))
                .build();
    }
}
