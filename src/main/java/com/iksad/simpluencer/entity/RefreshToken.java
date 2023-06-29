package com.iksad.simpluencer.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.Instant;

@EntityListeners(AuditingEntityListener.class)
@Entity
@Table(name = "refresh_token", indexes = {
        @Index(columnList = "provider"),
        @Index(columnList = "principalName")
})
@NoArgsConstructor @Getter @Setter
public class RefreshToken extends BaseEntity {
    private String provider;

    private String principalName;

    @Column(unique = true)
    private String refreshToken;

    private Instant issuedAtRefreshToken;

    private Instant expiresAtRefreshToken;
}
