package com.iksad.simpluencer.OAuth2.google;

import com.iksad.simpluencer.OAuth2.UserInfoApiRepository;
import com.iksad.simpluencer.model.ClientRegistration;
import com.iksad.simpluencer.model.response.OAuth2TokenResponse;
import com.iksad.simpluencer.model.response.OAuth2UserInfoResponse;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

public class UserInfoApiRepositoryImpl implements UserInfoApiRepository {
    private final ParameterizedTypeReference<Map<String, Object>> typeReference = new ParameterizedTypeReference<>() {};

    @Override
    public OAuth2UserInfoResponse readUserInfo(ClientRegistration clientRegistration, OAuth2TokenResponse tokenResponse, RestTemplate restTemplate) {
        String userInfoUri = "https://www.googleapis.com/oauth2/v3/userinfo";

        HttpHeaders headers = getHeaders(tokenResponse.accessToken());
        HttpEntity<Void> httpEntity = new HttpEntity<>(null, headers);

        ResponseEntity<Map<String, Object>> response = restTemplate.exchange(userInfoUri, HttpMethod.GET, httpEntity, this.typeReference);
        Map<String, Object> responseBody = response.getBody();

        return parseResponse(responseBody);
    }

    private static HttpHeaders getHeaders(String accessToken) {
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(accessToken);
        headers.setContentType(MediaType.APPLICATION_JSON);
        return headers;
    }

    private static OAuth2UserInfoResponse parseResponse(Map<String, Object> responseBody) {
        String principalName = (String) responseBody.getOrDefault("sub", null);
        String principalAccount = (String) responseBody.getOrDefault("email", null);

        return OAuth2UserInfoResponse.builder()
                .principalName(principalName)
                .account(principalAccount)
                .build();
    }
}
