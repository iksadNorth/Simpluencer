package com.iksad.simpluencer.repository;

import com.iksad.simpluencer.model.ClientRegistration;
import com.iksad.simpluencer.model.response.OAuth2TokenResponse;
import com.iksad.simpluencer.model.response.OAuth2UserInfoResponse;
import com.iksad.simpluencer.service.OAuth2TokenManager;
import lombok.RequiredArgsConstructor;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@Component
@RequiredArgsConstructor
public class UserInfoApiRepositoryImpl implements UserInfoApiRepository {
    private final RestTemplate restTemplate;
    private final OAuth2TokenManager oAuth2TokenManager;
    private final ParameterizedTypeReference<Map<String, Object>> typeReference = new ParameterizedTypeReference<>() {};

    @Override
    public OAuth2UserInfoResponse readUserInfo(ClientRegistration clientRegistration, OAuth2TokenResponse tokenResponse) {
        String userInfoUri = clientRegistration.getUserInfoUri();

        HttpEntity<Void> httpEntity = getHttpEntityForJson(tokenResponse.accessToken());
        ResponseEntity<Map<String, Object>> response = restTemplate.exchange(userInfoUri, HttpMethod.GET, httpEntity, this.typeReference);
        Map<String, Object> responseBody = response.getBody();

        String keyOfId = clientRegistration.getUserNameAttributeName().getIdAttr();
        String principalName = (String) responseBody.getOrDefault(keyOfId, null);
        String principalAccount = (String) responseBody.getOrDefault(clientRegistration.getAccountAttributeName(), null);
        return OAuth2UserInfoResponse.builder()
                .principalName(principalName)
                .account(principalAccount)
                .build();
    }

    private static HttpEntity<Void> getHttpEntityForJson(String accessToken) {
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(accessToken);
        headers.setContentType(MediaType.APPLICATION_JSON);
        return new HttpEntity<>(null, headers);
    }

    private HttpEntity<Void> getHttpEntityForJson(String providerName, String principalName) {
        String accessToken = this.oAuth2TokenManager.getAccessToken(providerName, principalName);
        return getHttpEntityForJson(accessToken);
    }
}
