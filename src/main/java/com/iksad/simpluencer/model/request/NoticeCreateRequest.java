package com.iksad.simpluencer.model.request;

import lombok.Builder;
import org.springframework.web.multipart.MultipartFile;

@Builder(toBuilder = true)
public record NoticeCreateRequest(
        MultipartFile image,
        String content,
        Long[] platforms
) {
}
