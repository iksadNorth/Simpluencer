package com.iksad.simpluencer.model.response;

import com.iksad.simpluencer.entity.Notice;
import lombok.Builder;

@Builder(toBuilder = true)
public record NoticeReadResponse(
        Long id,
        String content,
        String image
) {
    public static NoticeReadResponse fromEntity(Notice entity) {
        return NoticeReadResponse.builder()
                .id(entity.getId())
                .content(entity.getContent())
                .image(entity.getImageUri())
                .build();
    }
}
