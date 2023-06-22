package com.iksad.simpluencer.config;

import com.iksad.simpluencer.Properties.ServerProperties;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.core.ClientAuthenticationMethod;
import org.springframework.security.oauth2.core.oidc.IdTokenClaimNames;

import static com.iksad.simpluencer.config.TemplateUtils.substituteProperties;

public enum OAuth2ProviderType {
    GOOGLE {

        @Override
        public ClientRegistration.Builder getBuilder(String registrationId, ServerProperties serverProperties) {
            ClientRegistration.Builder builder = getBuilder(registrationId,
                    ClientAuthenticationMethod.CLIENT_SECRET_BASIC, DEFAULT_REDIRECT_URL, serverProperties);
            builder.scope("profile", "email");
            builder.authorizationUri("https://accounts.google.com/o/oauth2/v2/auth");
            builder.tokenUri("https://www.googleapis.com/oauth2/v4/token");
            builder.userInfoUri("https://www.googleapis.com/oauth2/v3/userinfo");
            builder.userNameAttributeName(IdTokenClaimNames.SUB);
            builder.clientName("Google");
            return builder;
        }

    };

    private static final String DEFAULT_REDIRECT_URL = "{baseUrl}/login/oauth2/code/{registrationId}";

    protected final ClientRegistration.Builder getBuilder(String registrationId, ClientAuthenticationMethod method,
                                                          String redirectUri, ServerProperties serverProperties) {
        TemplateUtils.Args args = TemplateUtils.Args.builder()
                .registrationId(registrationId)
                .serverProperties(serverProperties)
                .build();

        ClientRegistration.Builder builder = ClientRegistration.withRegistrationId(registrationId);
        builder.clientAuthenticationMethod(method);
        builder.authorizationGrantType(AuthorizationGrantType.AUTHORIZATION_CODE);
        builder.redirectUri(substituteProperties(redirectUri, args));
        return builder;
    }

    public abstract ClientRegistration.Builder getBuilder(String registrationId, ServerProperties serverProperties);
}
