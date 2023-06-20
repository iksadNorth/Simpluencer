package com.iksad.simpluencer.utils;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.core.AuthorizationGrantType;

import static org.assertj.core.api.Assertions.assertThat;

@Slf4j
@DisplayName("[OAuthRequestUriUtils]")
class OAuthRequestUriUtilsTest {

    @Test @DisplayName("[getUri][정상]")
    void getUri() {
        // Given
        String clientId = "{clientId}";
        String clientSecret = "{clientSecret}";

        String provider = "google";
        String redirect_uri = "http://localhost:8080/login/oauth2/code/google";
        String[] scopes = new String[]{"email", "profile"};

        String authorizationUri = "https://accounts.google.com/o/oauth2/v2/auth";
        String tokenUri = "https://oauth2.googleapis.com/token";

        ClientRegistration registration = ClientRegistration.withRegistrationId(provider)

                .authorizationGrantType(AuthorizationGrantType.AUTHORIZATION_CODE)
                .clientId(clientId)
                .clientSecret(clientSecret)
                .redirectUri(redirect_uri)
                .scope(scopes)

                .authorizationUri(authorizationUri)
                .tokenUri(tokenUri)

                .build();

        // When
        String uri = OAuthRequestUriUtils.getUri(registration);
        log.info(uri);

        // then
        assertThat(uri).contains(
                "https://accounts.google.com/o/oauth2/v2/auth"
        );
        assertThat(uri).contains(
                "client_id=" + clientId
        );
        assertThat(uri).contains(
                "redirect_uri=" + redirect_uri
        );
        assertThat(uri).contains(
                "scope="
        );
        for(String scope : scopes) {
            assertThat(uri).contains(scope);
        }
        assertThat(uri).contains(
                "response_type=code"
        );
        assertThat(uri).contains(
                "access_type=offline"
        );

    }
}