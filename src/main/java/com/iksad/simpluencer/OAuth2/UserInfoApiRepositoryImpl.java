package com.iksad.simpluencer.OAuth2;

import com.iksad.simpluencer.model.ClientRegistration;
import com.iksad.simpluencer.model.response.OAuth2TokenResponse;
import com.iksad.simpluencer.model.response.OAuth2UserInfoResponse;
import com.iksad.simpluencer.type.OAuth2ProviderType;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class UserInfoApiRepositoryImpl implements UserInfoApiRepository {
    @Override
    public OAuth2UserInfoResponse readUserInfo(ClientRegistration clientRegistration, OAuth2TokenResponse tokenResponse, RestTemplate restTemplate) {
        OAuth2ProviderType type = OAuth2ProviderType.providerOf(clientRegistration.getRegistrationId());
        return type.getUserInfoApiRepository().readUserInfo(clientRegistration, tokenResponse, restTemplate);
    }
}
