package com.iksad.simpluencer.repository;

import com.iksad.simpluencer.entity.RefreshToken;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long> {
    Optional<RefreshToken> findByProviderAndPrincipalName(String clientRegistrationId, String principalName);
}
