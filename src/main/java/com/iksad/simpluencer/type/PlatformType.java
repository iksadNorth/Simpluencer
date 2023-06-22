package com.iksad.simpluencer.type;

import com.iksad.simpluencer.config.OAuth2ProviderType;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.stream.Stream;

@RequiredArgsConstructor @Getter
public enum PlatformType {
    GOOGLE("GOOGLE", "Google", "youtube.png", OAuth2ProviderType.GOOGLE);

    private final String provider;
    private final String frontName;
    private final String icon;
    private final OAuth2ProviderType providerType;

    public static PlatformType providerOf(String arg) {
        return Stream.of(PlatformType.values())
                .filter(s -> s.getProvider().equals(arg))
                .findFirst()
                .orElseThrow(IllegalArgumentException::new);
    }
}
