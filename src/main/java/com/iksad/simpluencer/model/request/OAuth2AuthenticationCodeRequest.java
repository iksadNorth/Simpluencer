package com.iksad.simpluencer.model.request;

import lombok.Builder;

@Builder(toBuilder = true)
public record OAuth2AuthenticationCodeRequest(
        String registrationId,
        String code
) {
}
