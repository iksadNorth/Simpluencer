package com.iksad.simpluencer.OAuth2.twitter;

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
//    https://developer.twitter.com/en/docs/authentication/oauth-2-0/user-access-token
    private final String tokenUri = "https://api.twitter.com/2/oauth2/token";

    private final ParameterizedTypeReference<Map<String, String>> typeReference = new ParameterizedTypeReference<>() {};

    private String getRedirectUri(ServerProperties serverProperties) {
        return TemplateUtils.substituteProperties(TemplateUtils.Args.builder()
                .serverProperties(serverProperties)
                .registrationId(OAuth2ProviderType.TWITTER.getProvider())
                .build());
    }

    @Override
    public OAuth2TokenResponse getByCode(String code, ClientRegistration clientRegistration, Args args) {
        HttpEntity<MultiValueMap<String, Object>> httpEntity = new HttpEntity<>(getBodyByCode(code, args.serverProperties()), getHeaders(clientRegistration));
        ResponseEntity<Map<String, String>> responseEntity = args.restTemplate().exchange(tokenUri, HttpMethod.POST, httpEntity, typeReference);
        return OAuth2TokenResponse.of(responseEntity.getBody());
    }

    @Override
    public OAuth2TokenResponse getByRefreshToken(String refreshToken, ClientRegistration clientRegistration, Args args) {
        HttpEntity<MultiValueMap<String, Object>> httpEntity = new HttpEntity<>(getBodyByRefreshToken(refreshToken, args.serverProperties()), getHeaders(clientRegistration));
        ResponseEntity<Map<String, String>> responseEntity = args.restTemplate().exchange(tokenUri, HttpMethod.POST, httpEntity, typeReference);
        return OAuth2TokenResponse.of(responseEntity.getBody());
    }

    private MultiValueMap<String, Object> getBodyByCode(String code, ServerProperties serverProperties) {
        MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
        body.add("redirect_uri", getRedirectUri(serverProperties));
        body.add("code", code);
        body.add("grant_type", "authorization_code");
        body.add("code_verifier", "challenge");
        return body;
    }

    private MultiValueMap<String, Object> getBodyByRefreshToken(String refreshToken, ServerProperties serverProperties) {
        MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
        body.add("refresh_token", refreshToken);
        body.add("grant_type", "refresh_token");
        return body;
    }

    private HttpHeaders getHeaders(ClientRegistration clientRegistration) {
        HttpHeaders headers = new HttpHeaders();
        headers.setBasicAuth(clientRegistration.getClientId(), clientRegistration.getClientSecret());
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        return headers;
    }
}
