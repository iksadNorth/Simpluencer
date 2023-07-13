package com.iksad.simpluencer.model.response;

import com.iksad.simpluencer.entity.Panel;
import com.iksad.simpluencer.type.OAuth2ProviderType;
import lombok.Builder;

@Builder(toBuilder = true)
public record PanelReadResponse(
        Long id,
        String frontName,
        String icon,
        String description,
        String email
) {
    public static PanelReadResponse of(Panel entity) {
        String provider = entity.getProvider();
        OAuth2ProviderType type = OAuth2ProviderType.providerOf(provider);
        return PanelReadResponse.builder()
                .id(entity.getId())
                .frontName(type.getFrontName())
                .icon(type.getIcon())
                .description(entity.getDescription())
                .email(entity.getAccount())
                .build();
    }
}
