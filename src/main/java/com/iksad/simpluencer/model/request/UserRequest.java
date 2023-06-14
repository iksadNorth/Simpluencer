package com.iksad.simpluencer.model.request;

import lombok.Builder;

@Builder(toBuilder = true)
public record UserRequest(
        String email,
        String password,
        String nickname
) {
}
