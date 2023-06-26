package com.iksad.simpluencer.model;

import lombok.Builder;

@Builder(toBuilder = true)
public record WebApiNoticeCreateDto(
        String content,
        String imageUri
) {
}
