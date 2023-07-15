package com.iksad.simpluencer.OAuth2.twitter;

import com.iksad.simpluencer.OAuth2.UserInfoApiRepository;
import com.iksad.simpluencer.model.ClientRegistration;
import com.iksad.simpluencer.model.response.OAuth2TokenResponse;
import com.iksad.simpluencer.model.response.OAuth2UserInfoResponse;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.LinkedHashMap;
import java.util.Map;

public class UserInfoApiRepositoryImpl implements UserInfoApiRepository {
//    https://developer.twitter.com/en/docs/twitter-api/users/lookup/api-reference/get-users-me
    private final ParameterizedTypeReference<Map<String, Object>> typeReference = new ParameterizedTypeReference<>() {};

    @Override
    public OAuth2UserInfoResponse readUserInfo(ClientRegistration clientRegistration, OAuth2TokenResponse tokenResponse, RestTemplate restTemplate) {
        String userInfoUri = "https://api.twitter.com/2/users/me";

        HttpHeaders headers = getHeaders(tokenResponse.accessToken());
        HttpEntity<Void> httpEntity = new HttpEntity<>(null, headers);

        ResponseEntity<Map<String, Object>> response = restTemplate.exchange(userInfoUri, HttpMethod.GET, httpEntity, this.typeReference);
        Map<String, Object> responseBody = response.getBody();

        return parseResponse(responseBody);
    }

    private static HttpHeaders getHeaders(String accessToken) {
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(accessToken);
        return headers;
    }

    private static OAuth2UserInfoResponse parseResponse(Map<String, Object> responseBody) {
        LinkedHashMap<String, String> data = (LinkedHashMap<String, String>) responseBody.get("data");
        String principalName = data.getOrDefault("id", null);
        String principalAccount = data.getOrDefault("username", null);

        return OAuth2UserInfoResponse.builder()
                .principalName(principalName)
                .account(principalAccount)
                .build();
    }
}
