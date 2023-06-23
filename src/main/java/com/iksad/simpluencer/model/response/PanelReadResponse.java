package com.iksad.simpluencer.model.response;

import com.iksad.simpluencer.entity.Panel;
import lombok.Builder;
import com.iksad.simpluencer.type.PlatformType;

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
        PlatformType type = PlatformType.providerOf(provider);
        return PanelReadResponse.builder()
                .id(entity.getId())
                .frontName(type.getFrontName())
                .icon(type.getIcon())
                .description(entity.getDescription())
                .email(entity.getEmail())
                .build();
    }
}
