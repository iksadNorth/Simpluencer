package com.iksad.simpluencer.OAuth2.twitter;

import com.iksad.simpluencer.OAuth2.OAuthRequestUriSupplier;
import com.iksad.simpluencer.Properties.ServerProperties;
import com.iksad.simpluencer.model.ClientRegistration;
import com.iksad.simpluencer.type.OAuth2ProviderType;
import com.iksad.simpluencer.utils.RandomUtils;
import com.iksad.simpluencer.utils.TemplateUtils;
import org.springframework.web.util.UriComponentsBuilder;

public class OAuthRequestUriSupplierImpl implements OAuthRequestUriSupplier {
    private final static String SCOPE_DELIMITER = " ";

    private String[] getScope() {
        return new String[]{"offline.access", "tweet.write", "tweet.read", "users.read"};
    }

    private String getAuthorizeUri() {
        return "https://twitter.com/i/oauth2/authorize";
    }

    @Override
    public String getUri(ClientRegistration clientRegistration, ServerProperties serverProperties) {
        String registrationId = OAuth2ProviderType.TWITTER.getProvider();

        String redirectUrl = TemplateUtils.substituteProperties(TemplateUtils.Args.builder()
                .serverProperties(serverProperties)
                .registrationId(registrationId)
                .build());

        UriComponentsBuilder builder = UriComponentsBuilder.fromUriString(getAuthorizeUri());
        builder.queryParam("state", RandomUtils.getState());
        builder.queryParam("code_challenge", "challenge");
        builder.queryParam("code_challenge_method", "plain");

        builder.queryParam("response_type", "code");

        builder.queryParam("client_id", clientRegistration.getClientId());
        builder.queryParam("redirect_uri", redirectUrl);
        builder.queryParam("scope", String.join(SCOPE_DELIMITER, getScope()));
        return builder.toUriString();
    }
}
