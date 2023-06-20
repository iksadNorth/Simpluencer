package com.iksad.simpluencer.repository;

import com.iksad.simpluencer.entity.Token;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TokenRepository extends JpaRepository<Token, Long> {
    Optional<Token> findByProviderAndPrincipalName(String clientRegistrationId, String principalName);

    void deleteByProviderAndPrincipalName(String clientRegistrationId, String principalName);
}
