package com.iksad.simpluencer.repository;

import com.iksad.simpluencer.model.ClientRegistration;
import com.iksad.simpluencer.model.response.OAuth2TokenResponse;
import lombok.Builder;
import lombok.RequiredArgsConstructor;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@Component
@RequiredArgsConstructor
public class OAuth2AccessTokenRepositoryImpl implements OAuth2AccessTokenRepository {
    private final RestTemplate restTemplate;
    private final ParameterizedTypeReference<Map<String, String>> typeReference = new ParameterizedTypeReference<>() {};

    @Override
    public OAuth2TokenResponse getAccessTokenByCode(ClientRegistration clientRegistration, String code) {
        String tokenUri = clientRegistration.getTokenUri();

        HttpEntity<OAuth2CodeRequest> httpEntity = CodeToAccessToken(clientRegistration, code);
        ResponseEntity<Map<String, String>> responseEntity = restTemplate.exchange(tokenUri, HttpMethod.POST, httpEntity, typeReference);
        Map<String, String> entityBody = responseEntity.getBody();
        return OAuth2TokenResponse.of(entityBody);
    }

    @Override
    public OAuth2TokenResponse getAccessTokenByRefreshCode(ClientRegistration clientRegistration, String refreshToken) {
        String tokenUri = clientRegistration.getTokenUri();

        HttpEntity<OAuth2RefreshRequest> httpEntity = RefreshTokenToAccessToken(clientRegistration, refreshToken);
        ResponseEntity<Map<String, String>> responseEntity = restTemplate.exchange(tokenUri, HttpMethod.POST, httpEntity, typeReference);
        Map<String, String> entityBody = responseEntity.getBody();
        return OAuth2TokenResponse.of(entityBody);
    }

    private static HttpEntity<OAuth2CodeRequest> CodeToAccessToken(ClientRegistration clientRegistration, String code) {
        return new HttpEntity<>(OAuth2CodeRequest.builder()
                .code(code)
                .clientId(clientRegistration.getClientId())
                .clientSecret(clientRegistration.getClientSecret())
                .redirectUri(clientRegistration.getRedirectUri())
                .grantType(clientRegistration.getAuthorizationGrantType().getCodeToAccessToken())
                .build());
    }

    private static HttpEntity<OAuth2RefreshRequest> RefreshTokenToAccessToken(ClientRegistration clientRegistration, String refreshToken) {
        return new HttpEntity<>(OAuth2RefreshRequest.builder()
                .refreshToken(refreshToken)
                .clientId(clientRegistration.getClientId())
                .clientSecret(clientRegistration.getClientSecret())
                .redirectUri(clientRegistration.getRedirectUri())
                .grantType(clientRegistration.getAuthorizationGrantType().getRefreshToAccessToken())
                .build());
    }

    @Builder(toBuilder = true)
    public static record OAuth2CodeRequest(
            String code,
            String clientId,
            String clientSecret,
            String redirectUri,
            String grantType
    ) {}

    @Builder(toBuilder = true)
    public static record OAuth2RefreshRequest(
            String refreshToken,
            String clientId,
            String clientSecret,
            String redirectUri,
            String grantType
    ) {}
}
