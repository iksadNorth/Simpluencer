package com.iksad.simpluencer.config;

import com.iksad.simpluencer.Properties.ServerProperties;
import com.iksad.simpluencer.model.ClientRegistration;
import com.iksad.simpluencer.repository.NoticeApiRepository.GoogleNoticeApiRepository;
import com.iksad.simpluencer.repository.NoticeApiRepository.RedditNoticeApiRepository;
import com.iksad.simpluencer.repository.NoticeApiRepository.ProviderNoticeApiRepository;
import com.iksad.simpluencer.service.OAuth2TokenManager;
import com.iksad.simpluencer.type.AuthorizationGrantType;
import com.iksad.simpluencer.type.ClientAuthenticationMethod;
import com.iksad.simpluencer.type.IdTokenClaimNames;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.web.client.RestTemplate;

import java.util.function.BiFunction;
import java.util.stream.Stream;

import static com.iksad.simpluencer.config.TemplateUtils.substituteProperties;

@RequiredArgsConstructor @Getter
public enum OAuth2ProviderType {
    GOOGLE("google", GoogleNoticeApiRepository::new) {

        @Override
        public ClientRegistration.ClientRegistrationBuilder getBuilder(String registrationId, ServerProperties serverProperties) {
            ClientRegistration.ClientRegistrationBuilder builder = getBuilder(registrationId,
                    ClientAuthenticationMethod.CLIENT_SECRET_BASIC, DEFAULT_REDIRECT_URL, serverProperties);
            builder.scope(new String[]{"profile", "email"});
            builder.authorizationUri("https://accounts.google.com/o/oauth2/v2/auth?access_type=offline&prompt=consent");
            builder.tokenUri("https://www.googleapis.com/oauth2/v4/token");
            builder.userInfoUri("https://www.googleapis.com/oauth2/v3/userinfo");
            builder.userNameAttributeName(IdTokenClaimNames.SUB);
            builder.clientName("Google");
            builder.accountAttributeName("email");
            return builder;
        }

    },

    REDDIT("reddit", RedditNoticeApiRepository::new) {

        @Override
        public ClientRegistration.ClientRegistrationBuilder getBuilder(String registrationId, ServerProperties serverProperties) {
            ClientRegistration.ClientRegistrationBuilder builder = getBuilder(registrationId,
                    ClientAuthenticationMethod.CLIENT_SECRET_BASIC, DEFAULT_REDIRECT_URL, serverProperties);
            builder.scope(new String[]{"submit", "identity"});
            builder.authorizationUri("https://www.reddit.com/api/v1/authorize?state=2i3h4kmn5u3nbh5i&duration=temporary");
            builder.tokenUri("https://www.reddit.com/api/v1/access_token");
            builder.userInfoUri("https://oauth.reddit.com/api/v1/me");
            builder.userNameAttributeName(IdTokenClaimNames.ID);
            builder.clientName("Reddit");
            builder.accountAttributeName("name");
            return builder;
        }

    };

    private static final String DEFAULT_REDIRECT_URL = "{baseUrl}/login/oauth2/code/{registrationId}";

    protected final ClientRegistration.ClientRegistrationBuilder getBuilder(String registrationId, ClientAuthenticationMethod method,
                                                          String redirectUri, ServerProperties serverProperties) {
        TemplateUtils.Args args = TemplateUtils.Args.builder()
                .registrationId(registrationId)
                .serverProperties(serverProperties)
                .build();

        ClientRegistration.ClientRegistrationBuilder builder = ClientRegistration.withRegistrationId(registrationId);
        builder.clientAuthenticationMethod(method);
        builder.authorizationGrantType(AuthorizationGrantType.AUTHORIZATION_CODE);
        builder.redirectUri(substituteProperties(redirectUri, args));
        return builder;
    }

    public abstract ClientRegistration.ClientRegistrationBuilder getBuilder(String registrationId, ServerProperties serverProperties);
    public final String providerNameForRedirectUrl;
    public final BiFunction<RestTemplate, OAuth2TokenManager, ProviderNoticeApiRepository> constructorNoticeApiRepository;

    public static OAuth2ProviderType findWithProviderNameForRedirectUrl(String providerNameForRedirectUrl) {
        return Stream.of(OAuth2ProviderType.values())
                .filter(type -> type.getProviderNameForRedirectUrl().equals(providerNameForRedirectUrl))
                .findFirst().orElseThrow(IllegalArgumentException::new);
    }
}
