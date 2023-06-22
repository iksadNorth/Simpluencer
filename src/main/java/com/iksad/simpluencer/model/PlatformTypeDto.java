package com.iksad.simpluencer.model;

import com.iksad.simpluencer.type.PlatformType;
import com.iksad.simpluencer.utils.OAuthRequestUriUtils;
import lombok.Builder;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;

@Builder(toBuilder = true)
public record PlatformTypeDto(
        String provider,
        String frontName,
        String icon,
        String redirectURL
) {
    public static PlatformTypeDto of(PlatformType type, ClientRegistrationRepository repository) {
        String typeProvider = type.getProvider();
        ClientRegistration clientRegistration = repository.findByRegistrationId(typeProvider);
        String redirectURL = OAuthRequestUriUtils.getUri(clientRegistration);
        return PlatformTypeDto.builder()
                .provider(typeProvider)
                .frontName(type.getFrontName())
                .icon(type.getIcon())
                .redirectURL(redirectURL)
                .build();
    }

    public static PlatformTypeDto of(PlatformType type) {
        String typeProvider = type.getProvider();
        return PlatformTypeDto.builder()
                .provider(typeProvider)
                .frontName(type.getFrontName())
                .icon(type.getIcon())
                .build();
    }
}
