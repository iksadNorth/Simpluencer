package com.iksad.simpluencer.model;

import lombok.Builder;

@Builder(toBuilder = true)
public record ParsedExceptionResult(
        String reason,
        String column,
        String input
) {}