package com.iksad.simpluencer.model.response;

import com.iksad.simpluencer.entity.Agent;
import com.iksad.simpluencer.model.AgentDto;
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

    public static ProfileResponse of(AgentDto dto) {
        return ProfileResponse.builder()
                .nickname(dto.nickname())
                .profileImage(dto.profileImage())
                .introduction(dto.introduction())
                .build();
    }
}
