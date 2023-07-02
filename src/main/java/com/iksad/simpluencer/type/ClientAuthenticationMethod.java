package com.iksad.simpluencer.type;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor @Getter
public enum ClientAuthenticationMethod {
    CLIENT_SECRET_POST,
    CLIENT_SECRET_BASIC;
}
