package com.iksad.simpluencer.type;

import com.iksad.simpluencer.OAuth2.OAuth2AccessTokenRepository;
import com.iksad.simpluencer.OAuth2.OAuthRequestUriSupplier;
import com.iksad.simpluencer.OAuth2.UserInfoApiRepository;
import com.iksad.simpluencer.OAuth2.NoticeApiRepository;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.stream.Stream;

@RequiredArgsConstructor @Getter
public enum OAuth2ProviderType {
    GOOGLE(
            "google",
            "GOOGLE",
            "Google",
            "youtube.png",
            new com.iksad.simpluencer.OAuth2.google.OAuthRequestUriSupplierImpl(),
            new com.iksad.simpluencer.OAuth2.google.OAuth2AccessTokenRepositoryImpl(),
            new com.iksad.simpluencer.OAuth2.google.UserInfoApiRepositoryImpl(),
            null
    ),

    REDDIT(
            "reddit",
            "REDDIT",
            "Reddit",
            "reddit.png",
            new com.iksad.simpluencer.OAuth2.reddit.OAuthRequestUriSupplierImpl(),
            new com.iksad.simpluencer.OAuth2.reddit.OAuth2AccessTokenRepositoryImpl(),
            new com.iksad.simpluencer.OAuth2.reddit.UserInfoApiRepositoryImpl(),
            new com.iksad.simpluencer.OAuth2.reddit.NoticeApiRepositoryImpl()
    ),

    TWITTER(
            "twitter",
            "TWITTER",
            "Twitter",
            "twitter.png",
            new com.iksad.simpluencer.OAuth2.twitter.OAuthRequestUriSupplierImpl(),
            new com.iksad.simpluencer.OAuth2.twitter.OAuth2AccessTokenRepositoryImpl(),
            new com.iksad.simpluencer.OAuth2.twitter.UserInfoApiRepositoryImpl(),
            new com.iksad.simpluencer.OAuth2.twitter.NoticeApiRepositoryImpl()
    );

    public final String providerNameForRedirectUrl;
    private final String provider;
    private final String frontName;
    private final String icon;

    private final OAuthRequestUriSupplier oAuthRequestUriSupplier;
    private final OAuth2AccessTokenRepository oAuth2AccessTokenRepository;
    private final UserInfoApiRepository userInfoApiRepository;
    private final NoticeApiRepository noticeApiRepository;

    public static OAuth2ProviderType findWithProviderNameForRedirectUrl(String providerNameForRedirectUrl) {
        return Stream.of(OAuth2ProviderType.values())
                .filter(type -> type.getProviderNameForRedirectUrl().equals(providerNameForRedirectUrl))
                .findFirst().orElseThrow(IllegalArgumentException::new);
    }

    public static OAuth2ProviderType providerOf(String arg) {
        return Stream.of(OAuth2ProviderType.values())
                .filter(s -> s.getProvider().equals(arg))
                .findFirst()
                .orElseThrow(IllegalArgumentException::new);
    }
}
