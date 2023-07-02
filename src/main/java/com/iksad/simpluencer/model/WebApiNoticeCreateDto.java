package com.iksad.simpluencer.model;

import lombok.Builder;

@Builder(toBuilder = true)
public record WebApiNoticeCreateDto(
        String content,
        String imageUri
) {
    public String getRealUri() {
        String imgDir = "/img";
        return String.format("%s%s:%s%s/%s",
                "http://", "localhost", 8080,
                imgDir, this.imageUri
        );
    }
}
