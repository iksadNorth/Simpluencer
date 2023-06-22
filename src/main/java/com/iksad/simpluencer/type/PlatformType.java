package com.iksad.simpluencer.type;

import com.iksad.simpluencer.service.PanelDtoFactory.GooglePanelDtoFactory;
import com.iksad.simpluencer.service.PanelDtoFactory.OAuth2PanelDtoFactory;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.stream.Stream;

@RequiredArgsConstructor @Getter
public enum PlatformType {
    GOOGLE("google", new GooglePanelDtoFactory(), "Google", "youtube.png");

    private final String provider;
    private final OAuth2PanelDtoFactory OAuth2PanelDtoFactory;
    private final String frontName;
    private final String icon;

    public static PlatformType providerOf(String arg) {
        return Stream.of(PlatformType.values())
                .filter(s -> s.getProvider().equals(arg))
                .findFirst()
                .orElseThrow(IllegalArgumentException::new);
    }
}
