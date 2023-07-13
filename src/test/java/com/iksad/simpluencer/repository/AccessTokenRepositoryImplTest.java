package com.iksad.simpluencer.repository;

import com.iksad.simpluencer.config.RedisConfig;
import com.iksad.simpluencer.entity.AccessToken;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.data.redis.RedisProperties;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@Slf4j
@DataJpaTest
@Import({RedisConfig.class, RedisProperties.class, AccessTokenRepositoryImpl.class})
@DisplayName("[AccessTokenRepositoryImpl]")
class AccessTokenRepositoryImplTest {
    @Autowired AccessTokenRepository accessTokenRepository;

    @Test @DisplayName("[save][findByProviderAndPrincipalName][정상]")
    void save() {
        // Given
        AccessToken token = new AccessToken();
        token.setProvider("GOOGLE");
        token.setPrincipalName("1234566543566542");
        token.setToken("3huri4j4jrigugfduwikme");
        token.setIssuedAt(Instant.now());
        token.setExpiresAt(Instant.now().plus(4345L, ChronoUnit.SECONDS));

        // When
        accessTokenRepository.save(token);

        // then
        Optional<AccessToken> accessToken = accessTokenRepository.findByProviderAndPrincipalName(token.getProvider(), token.getPrincipalName());
        assertThat(accessToken.isPresent()).isTrue();
        log.info(accessToken.get().getToken());
    }

    @Test @DisplayName("[deleteByProviderAndPrincipalName][정상]")
    void deleteByProviderAndPrincipalName() {
        // Given
        AccessToken token = new AccessToken();
        token.setProvider("GOOGLE");
        token.setPrincipalName("1234566543566542");
        token.setToken("3huri4j4jrigugfduwikme");
        token.setIssuedAt(Instant.now());
        token.setExpiresAt(Instant.now().plus(4345L, ChronoUnit.SECONDS));

        // When
        accessTokenRepository.deleteByProviderAndPrincipalName(token.getProvider(), token.getPrincipalName());

        // then
        Optional<AccessToken> accessToken = accessTokenRepository.findByProviderAndPrincipalName(token.getProvider(), token.getPrincipalName());
        assertThat(accessToken.isPresent()).isFalse();
    }
}