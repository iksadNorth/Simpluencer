package com.iksad.simpluencer.model;

import com.iksad.simpluencer.Properties.ServerProperties;
import com.iksad.simpluencer.repository.ClientRegistrationRepository;
import com.iksad.simpluencer.type.OAuth2ProviderType;
import lombok.Builder;

@Builder(toBuilder = true)
public record PlatformTypeDto(
        String provider,
        String frontName,
        String icon,
        String redirectURL
) {
    public static PlatformTypeDto of(OAuth2ProviderType type, String redirectURL) {
        return PlatformTypeDto.builder()
                .provider(type.getProvider())
                .frontName(type.getFrontName())
                .icon(type.getIcon())
                .redirectURL(redirectURL)
                .build();
    }

    public static PlatformTypeDto of(OAuth2ProviderType type) {
        return PlatformTypeDto.builder()
                .provider(type.getProvider())
                .frontName(type.getFrontName())
                .icon(type.getIcon())
                .build();
    }
}
