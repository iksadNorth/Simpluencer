package com.iksad.simpluencer.model.response;

import com.iksad.simpluencer.entity.Agent;
import lombok.Builder;

import java.util.List;

@Builder(toBuilder = true)
public record ProfileResponse(
        String nickname,
        String profileImage,
        String introduction,
        List<PanelReadResponse> panels
) {

    public static ProfileResponse fromEntity(Agent entity) {
        List<PanelReadResponse> panels = entity.getPanels().stream()
                .map(PanelReadResponse::of)
                .toList();
        return ProfileResponse.builder()
                .nickname(entity.getNickname())
                .profileImage(entity.getProfileImage())
                .introduction(entity.getIntroduction())
                .panels(panels)
                .build();
    }
}
