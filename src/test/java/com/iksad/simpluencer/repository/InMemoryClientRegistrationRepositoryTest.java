package com.iksad.simpluencer.repository;

import com.iksad.simpluencer.model.ClientRegistration;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@Slf4j
@SpringBootTest
class InMemoryClientRegistrationRepositoryTest {
    @Autowired private ClientRegistrationRepository clientRegistrationRepository;

    @Test @DisplayName("[findByRegistrationId][정상]")
    void findByRegistrationId() {
        ClientRegistration google = clientRegistrationRepository.findByRegistrationId("GOOGLE");

        assertThat(google.getClientId()).isEqualTo("GOOGLE_OAUTH_CLIENT_ID");
        assertThat(google.getClientSecret()).isEqualTo("GOOGLE_OAUTH_SECRET");
    }
}