package com.iksad.simpluencer.repository;

import com.iksad.simpluencer.model.ClientRegistration;
import com.iksad.simpluencer.model.response.OAuth2TokenResponse;
import com.iksad.simpluencer.type.ClientAuthenticationMethod;
import lombok.RequiredArgsConstructor;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
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

        HttpEntity<MultiValueMap<String, Object>> httpEntity = CodeToAccessToken(clientRegistration, code);
        ResponseEntity<Map<String, String>> responseEntity = restTemplate.exchange(tokenUri, HttpMethod.POST, httpEntity, typeReference);
        Map<String, String> entityBody = responseEntity.getBody();
        return OAuth2TokenResponse.of(entityBody);
    }

    @Override
    public OAuth2TokenResponse getAccessTokenByRefreshCode(ClientRegistration clientRegistration, String refreshToken) {
        String tokenUri = clientRegistration.getTokenUri();

        HttpEntity<MultiValueMap<String, Object>> httpEntity = RefreshTokenToAccessToken(clientRegistration, refreshToken);
        ResponseEntity<Map<String, String>> responseEntity = restTemplate.exchange(tokenUri, HttpMethod.POST, httpEntity, typeReference);
        Map<String, String> entityBody = responseEntity.getBody();
        return OAuth2TokenResponse.of(entityBody);
    }

    private static HttpEntity<MultiValueMap<String, Object>> CodeToAccessToken(ClientRegistration clientRegistration, String code) {
        MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
        body.add("code", code);
        body.add("redirect_uri", clientRegistration.getRedirectUri());
        body.add("grant_type", clientRegistration.getAuthorizationGrantType().getCodeToAccessToken());
        if (clientRegistration.getClientAuthenticationMethod().equals(ClientAuthenticationMethod.CLIENT_SECRET_POST)) {
            body.add("client_id", clientRegistration.getClientId());
            body.add("client_secret",  clientRegistration.getClientSecret());
        }

        return new HttpEntity<>(body, getHeaders(clientRegistration));
    }

    private static HttpEntity<MultiValueMap<String, Object>> RefreshTokenToAccessToken(ClientRegistration clientRegistration, String refreshToken) {
        MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
        body.add("refresh_token", refreshToken);
        body.add("redirect_uri", clientRegistration.getRedirectUri());
        body.add("grant_type", clientRegistration.getAuthorizationGrantType().getRefreshToAccessToken());
        if (clientRegistration.getClientAuthenticationMethod().equals(ClientAuthenticationMethod.CLIENT_SECRET_POST)) {
            body.add("client_id", clientRegistration.getClientId());
            body.add("client_secret",  clientRegistration.getClientSecret());
        }

        return new HttpEntity<>(body, getHeaders(clientRegistration));
    }

    private static HttpHeaders getHeaders(ClientRegistration clientRegistration) {
        HttpHeaders headers = new HttpHeaders();
        if (clientRegistration.getClientAuthenticationMethod().equals(ClientAuthenticationMethod.CLIENT_SECRET_BASIC)) {
            headers.setBasicAuth(clientRegistration.getClientId(), clientRegistration.getClientSecret());
            headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        }

        return headers;
    }
}
