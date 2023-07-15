package com.iksad.simpluencer.OAuth2;

import com.iksad.simpluencer.model.ClientRegistration;
import com.iksad.simpluencer.model.response.OAuth2TokenResponse;
import com.iksad.simpluencer.model.response.OAuth2UserInfoResponse;
import org.springframework.web.client.RestTemplate;

public interface UserInfoApiRepository {
    OAuth2UserInfoResponse readUserInfo(ClientRegistration clientRegistration, OAuth2TokenResponse tokenResponse, RestTemplate restTemplate);
}
