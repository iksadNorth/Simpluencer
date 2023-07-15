package com.iksad.simpluencer.repository;

import com.iksad.simpluencer.Properties.OAuth2ClientProperties;
import com.iksad.simpluencer.exception.ErrorType.ProviderNotFoundType;
import com.iksad.simpluencer.model.ClientRegistration;

import java.util.Map;
import java.util.stream.Collectors;

public class InMemoryClientRegistrationRepository implements ClientRegistrationRepository {
    private final Map<String, ClientRegistration> clientRegistrations;

    public InMemoryClientRegistrationRepository(OAuth2ClientProperties properties) {
        this.clientRegistrations = properties.getRegistration().entrySet().stream()
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        ClientRegistration::fromRegistration
                ));
    }

    @Override
    public ClientRegistration findByRegistrationId(String typeProvider) {
        try {
            return clientRegistrations.get(typeProvider);
        } catch (NullPointerException e) {
            throw new ProviderNotFoundType(typeProvider);
        }
    }
}
