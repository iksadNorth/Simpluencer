package com.iksad.simpluencer.model.response;

import lombok.Builder;

@Builder(toBuilder = true)
public record ErrorResponse(
        String errorMessage
) {
}
