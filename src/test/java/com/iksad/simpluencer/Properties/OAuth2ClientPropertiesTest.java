package com.iksad.simpluencer.Properties;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

@Slf4j
@SpringBootTest
class OAuth2ClientPropertiesTest {
    @Autowired private OAuth2ClientProperties properties;

    @Test
    void setProperties() {
        // given
        Map<String, OAuth2ClientProperties.Registration> registration = properties.getRegistration();

        // when
        OAuth2ClientProperties.Registration google = registration.get("GOOGLE");

        // then
        assertThat(google.getClientId()).isEqualTo("GOOGLE_OAUTH_CLIENT_ID");
        assertThat(google.getClientSecret()).isEqualTo("GOOGLE_OAUTH_SECRET");
    }
}
