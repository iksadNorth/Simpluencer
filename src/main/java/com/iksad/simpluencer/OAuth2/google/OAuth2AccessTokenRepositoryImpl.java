package com.iksad.simpluencer.OAuth2.google;

import com.iksad.simpluencer.OAuth2.OAuth2AccessTokenRepository;
import com.iksad.simpluencer.Properties.ServerProperties;
import com.iksad.simpluencer.model.ClientRegistration;
import com.iksad.simpluencer.model.response.OAuth2TokenResponse;
import com.iksad.simpluencer.type.OAuth2ProviderType;
import com.iksad.simpluencer.utils.TemplateUtils;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.util.Map;

public class OAuth2AccessTokenRepositoryImpl implements OAuth2AccessTokenRepository {
    private final String tokenUri = "https://www.googleapis.com/oauth2/v4/token";

    private final ParameterizedTypeReference<Map<String, String>> typeReference = new ParameterizedTypeReference<>() {};

    private String getRedirectUri(ServerProperties serverProperties) {
        return TemplateUtils.substituteProperties(TemplateUtils.Args.builder()
                .serverProperties(serverProperties)
                .registrationId(OAuth2ProviderType.GOOGLE.getProviderNameForRedirectUrl())
                .build());
    }

    @Override
    public OAuth2TokenResponse getByCode(String code, ClientRegistration clientRegistration, Args args) {
        HttpEntity<MultiValueMap<String, Object>> httpEntity = new HttpEntity<>(getBodyByCode(clientRegistration, code, args.serverProperties()), getHeaders());
        ResponseEntity<Map<String, String>> responseEntity = args.restTemplate().exchange(tokenUri, HttpMethod.POST, httpEntity, typeReference);
        return OAuth2TokenResponse.of(responseEntity.getBody());
    }

    @Override
    public OAuth2TokenResponse getByRefreshToken(String refreshToken, ClientRegistration clientRegistration, Args args) {
        HttpEntity<MultiValueMap<String, Object>> httpEntity = new HttpEntity<>(getBodyByRefreshToken(clientRegistration, refreshToken, args.serverProperties()), getHeaders());
        ResponseEntity<Map<String, String>> responseEntity = args.restTemplate().exchange(tokenUri, HttpMethod.POST, httpEntity, typeReference);
        return OAuth2TokenResponse.of(responseEntity.getBody());
    }

    private MultiValueMap<String, Object> getBodyByCode(ClientRegistration clientRegistration, String code, ServerProperties serverProperties) {
        MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
        body.add("redirect_uri", getRedirectUri(serverProperties));
        body.add("code", code);
        body.add("grant_type", "authorization_code");
        body.add("client_id", clientRegistration.getClientId());
        body.add("client_secret",  clientRegistration.getClientSecret());
        return body;
    }

    private MultiValueMap<String, Object> getBodyByRefreshToken(ClientRegistration clientRegistration, String refreshToken, ServerProperties serverProperties) {
        MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
        body.add("redirect_uri", getRedirectUri(serverProperties));
        body.add("refresh_token", refreshToken);
        body.add("grant_type", "refresh_token");
        body.add("client_id", clientRegistration.getClientId());
        body.add("client_secret",  clientRegistration.getClientSecret());
        return body;
    }

    private HttpHeaders getHeaders() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        return headers;
    }
}
