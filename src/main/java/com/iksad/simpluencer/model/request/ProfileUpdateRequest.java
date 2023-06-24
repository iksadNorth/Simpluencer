package com.iksad.simpluencer.model.request;

import lombok.Builder;
import org.springframework.web.multipart.MultipartFile;

@Builder(toBuilder = true)
public record ProfileUpdateRequest(
        String nickname,
        String introduction,
        MultipartFile image,
        Long[] locations
) {
}
