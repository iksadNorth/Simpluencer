package com.iksad.simpluencer.repository;

import com.iksad.simpluencer.entity.AccessToken;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class AccessTokenRepositoryImpl implements AccessTokenRepository {
    private final HashOperations<String, Object, Object> opsForHash;

    @Override
    public Optional<AccessToken> findByProviderAndPrincipalName(String clientRegistrationId, String principalName) {
        AccessToken accessToken = (AccessToken) opsForHash.get(clientRegistrationId, principalName);
        return Optional.ofNullable(accessToken);
    }

    @Override
    public void deleteByProviderAndPrincipalName(String clientRegistrationId, String principalName) {
        if(opsForHash.hasKey(clientRegistrationId, principalName)) {
            opsForHash.delete(clientRegistrationId, principalName);
        }
    }

    @Override
    public void save(AccessToken entity) {
        String provider = entity.getProvider();
        String principalName = entity.getPrincipalName();
        opsForHash.put(provider, principalName, entity);
    }
}
