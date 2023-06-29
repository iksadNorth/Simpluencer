package com.iksad.simpluencer.model.response;

import lombok.Builder;

@Builder(toBuilder = true)
public record OAuth2UserInfoResponse(
        String principalName,
        String account
) {
}
