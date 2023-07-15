package com.iksad.simpluencer.model;

import com.iksad.simpluencer.Properties.OAuth2ClientProperties;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.util.Map;

@Builder(toBuilder = true)
@RequiredArgsConstructor @Getter @Setter
public final class ClientRegistration {
    private final String registrationId;

    private final String clientId;
    private final String clientSecret;

    public static ClientRegistration fromRegistration(String registrationId, OAuth2ClientProperties.Registration registration) {
        return ClientRegistration.builder()
                .registrationId(registrationId)

                .clientId(registration.getClientId())
                .clientSecret(registration.getClientSecret())
                .build();
    }

    public static ClientRegistration fromRegistration(Map.Entry<String, OAuth2ClientProperties.Registration> entry) {
        return fromRegistration(entry.getKey(), entry.getValue());
    }
}
