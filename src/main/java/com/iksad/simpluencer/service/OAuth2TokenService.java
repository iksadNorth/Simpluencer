package com.iksad.simpluencer.service;

import com.iksad.simpluencer.model.request.OAuth2AuthenticationCodeRequest;

public interface OAuth2TokenService {
    void handleAuthenticationCode(OAuth2AuthenticationCodeRequest request);
}
