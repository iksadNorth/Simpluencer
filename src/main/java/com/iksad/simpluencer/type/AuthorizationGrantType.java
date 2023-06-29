package com.iksad.simpluencer.type;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor @Getter
public enum AuthorizationGrantType {
    AUTHORIZATION_CODE("code", "authorization_code", "refresh_token");

    private final String responseType;
    private final String codeToAccessToken;
    private final String refreshToAccessToken;
}
