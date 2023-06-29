package com.iksad.simpluencer.repository;

import com.iksad.simpluencer.entity.AccessToken;

import java.util.Optional;

public interface AccessTokenRepository {
    Optional<AccessToken> findByProviderAndPrincipalName(String clientRegistrationId, String principalName);
    void deleteByProviderAndPrincipalName(String clientRegistrationId, String principalName);
    void save(AccessToken entity);
}
