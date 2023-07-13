package com.iksad.simpluencer.repository;

import com.iksad.simpluencer.entity.RefreshToken;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@Slf4j
@ActiveProfiles(profiles = {"datasource-prod", "mysql-prod"})
@DataJpaTest
@DisplayName("[MySQLRefreshTokenRepositoryTest]")
class MySQLRefreshTokenRepositoryTest {
    @Autowired RefreshTokenRepository refreshTokenRepository;

    @Test @DisplayName("[findByProviderAndPrincipalName][정상]")
    @Sql(scripts = {"classpath:/data_init/create_refresh_token.sql"})
    void findByProviderAndPrincipalName() {
        // Given


        // When
        Optional<RefreshToken> optionalRefreshToken = refreshTokenRepository.findByProviderAndPrincipalName("google", "7kdi8wj2");

        // then
        assertThat(optionalRefreshToken.isPresent()).isTrue();
        log.info(optionalRefreshToken.get().getToken());
    }
}