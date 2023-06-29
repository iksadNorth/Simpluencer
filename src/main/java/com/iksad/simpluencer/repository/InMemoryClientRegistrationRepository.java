package com.iksad.simpluencer.repository;

import com.iksad.simpluencer.exception.ErrorType.ProviderNotFoundType;
import com.iksad.simpluencer.model.ClientRegistration;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public class InMemoryClientRegistrationRepository implements ClientRegistrationRepository {
    private final Map<String, ClientRegistration> clientRegistrations;

    public InMemoryClientRegistrationRepository(@Autowired List<ClientRegistration> clientRegistrations) {
        this.clientRegistrations = clientRegistrations.stream()
                .collect(Collectors.toMap(ClientRegistration::getRegistrationId, Function.identity()));
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
