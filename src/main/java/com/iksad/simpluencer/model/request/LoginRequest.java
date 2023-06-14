package com.iksad.simpluencer.model.request;

import lombok.Builder;

@Builder(toBuilder = true)
public record LoginRequest(
        String email,
        String password
) {
}
