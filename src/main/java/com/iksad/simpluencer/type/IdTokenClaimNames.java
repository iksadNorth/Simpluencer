package com.iksad.simpluencer.type;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor @Getter
public enum IdTokenClaimNames {
    ID("id"),
    SUB("sub");

    private final String idAttr;
}
