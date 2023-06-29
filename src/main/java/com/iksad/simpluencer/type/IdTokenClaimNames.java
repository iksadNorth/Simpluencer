package com.iksad.simpluencer.type;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor @Getter
public enum IdTokenClaimNames {
    SUB("sub");

    private final String idAttr;
}
