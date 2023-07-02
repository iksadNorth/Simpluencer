package com.iksad.simpluencer.model;

import com.iksad.simpluencer.Properties.OAuth2ClientProperties;
import com.iksad.simpluencer.type.AuthorizationGrantType;
import com.iksad.simpluencer.type.ClientAuthenticationMethod;
import com.iksad.simpluencer.type.IdTokenClaimNames;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Builder(toBuilder = true)
@RequiredArgsConstructor @Getter
public final class ClientRegistration {
    private final String registrationId;

    private final ClientAuthenticationMethod clientAuthenticationMethod;
    private final AuthorizationGrantType authorizationGrantType;
    private final String clientId;
    private final String clientSecret;
    private final String clientName;
    private final String redirectUri;
    private final String[] scope;

    private final String authorizationUri;
    private final String tokenUri;
    private final String userInfoUri;
    private final IdTokenClaimNames userNameAttributeName;
    private final String accountAttributeName;

    public static ClientRegistrationBuilder withRegistrationId(String registrationId) {
        return ClientRegistration.builder().registrationId(registrationId);
    }

    public Set<String> getScopes() {
        return new HashSet<>(List.of(this.scope));
    }

    public OAuth2ClientProperties.Registration getRegistration() {
        return OAuth2ClientProperties.Registration.builder()
                .clientAuthenticationMethod(this.clientAuthenticationMethod.name())
                .authorizationGrantType(this.authorizationGrantType.getResponseType())
                .clientId(this.clientId)
                .clientSecret(this.clientSecret)
                .clientName(this.clientName)
                .redirectUri(this.redirectUri)
                .scope(new HashSet<>(List.of(this.scope)))
                .build();
    }

    public OAuth2ClientProperties.Provider getProviderDetails() {
        return OAuth2ClientProperties.Provider.builder()
                .authorizationUri(this.authorizationUri)
                .tokenUri(this.tokenUri)
                .userInfoUri(this.userInfoUri)
                .userNameAttribute(this.userNameAttributeName.getIdAttr())
                .build();
    }
}
