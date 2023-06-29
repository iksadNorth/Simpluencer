package com.iksad.simpluencer.service;

public interface OAuth2TokenManager {
    String getAccessToken(String providerName, String principalName);
}
