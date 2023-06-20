package com.iksad.simpluencer.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.core.OAuth2AccessToken;
import org.springframework.security.oauth2.core.OAuth2RefreshToken;

import java.time.Instant;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Entity
@Table(name = "token", indexes = {
        @Index(columnList = "provider"),
        @Index(columnList = "principalName")
})
@NoArgsConstructor @Getter @Setter
public class Token extends BaseEntity {
    private String provider;

    private String principalName;

    @Column(unique = true)
    private String refreshToken;

    private Instant issuedAtRefreshToken;

    private Instant expiresAtRefreshToken;

    @Column(unique = true)
    private String accessToken;

    private Instant issuedAtAccessToken;

    private Instant expiresAtAccessToken;

    @OneToMany(mappedBy = "token", cascade = {CascadeType.PERSIST, CascadeType.REMOVE})
    private Set<Scope> scopes;

    public static Token fromOAuth2AuthorizedClient(OAuth2AuthorizedClient authorizedClient) {
        Token entity = new Token();
        entity.setProvider(authorizedClient.getClientRegistration().getClientId());
        entity.setPrincipalName(authorizedClient.getPrincipalName());

        entity.setAccessToken(authorizedClient.getAccessToken().getTokenValue());
        entity.setIssuedAtAccessToken(authorizedClient.getAccessToken().getIssuedAt());
        entity.setExpiresAtAccessToken(authorizedClient.getAccessToken().getExpiresAt());

        entity.setRefreshToken(authorizedClient.getRefreshToken().getTokenValue());
        entity.setIssuedAtRefreshToken(authorizedClient.getRefreshToken().getIssuedAt());
        entity.setExpiresAtRefreshToken(authorizedClient.getRefreshToken().getExpiresAt());

        Set<Scope> scopeTable = new HashSet<>();
        for(String scope : authorizedClient.getAccessToken().getScopes()) {
            Scope scopeEntity = new Scope();
            scopeEntity.setToken(entity);
            scopeEntity.setScope(scope);
            scopeTable.add(scopeEntity);
        }
        entity.setScopes(scopeTable);
        return entity;
    }

    public OAuth2AccessToken getOAuth2AccessToken() {
        return new OAuth2AccessToken(
                OAuth2AccessToken.TokenType.BEARER,
                this.getAccessToken(),
                this.getIssuedAtAccessToken(),
                this.getExpiresAtAccessToken(),
                this.getScopes().stream()
                        .map(Scope::getScope)
                        .collect(Collectors.toUnmodifiableSet())
        );
    }

    public OAuth2RefreshToken getOAuth2RefreshToken() {
        return new OAuth2RefreshToken(
                this.getRefreshToken(),
                this.getIssuedAtRefreshToken(),
                this.getExpiresAtRefreshToken()
        );
    }
}
