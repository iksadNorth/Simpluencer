package com.iksad.simpluencer.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.time.Instant;

@NoArgsConstructor @Getter @Setter
public class AccessToken implements Serializable {
    private String provider;

    private String principalName;

    private String accessToken;

    private Instant issuedAtAccessToken;

    private Instant expiresAtAccessToken;
}
