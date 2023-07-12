package com.iksad.simpluencer.utils;

import com.iksad.simpluencer.model.ClientRegistration;
import com.iksad.simpluencer.tools.MockSecurityContextFactory.WithMockPrincipal;
import com.iksad.simpluencer.type.AuthorizationGrantType;
import com.iksad.simpluencer.type.IdTokenClaimNames;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

@Slf4j
@WithMockPrincipal
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
                .userNameAttributeName(IdTokenClaimNames.SUB)

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
    }
}