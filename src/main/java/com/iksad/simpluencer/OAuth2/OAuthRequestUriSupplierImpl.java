package com.iksad.simpluencer.OAuth2;

import com.iksad.simpluencer.Properties.ServerProperties;
import com.iksad.simpluencer.model.ClientRegistration;
import com.iksad.simpluencer.type.OAuth2ProviderType;
import org.springframework.stereotype.Component;

@Component
public class OAuthRequestUriSupplierImpl implements OAuthRequestUriSupplier {
    @Override
    public String getUri(ClientRegistration clientRegistration, ServerProperties serverProperties) {
        String registrationId = clientRegistration.getRegistrationId();
        OAuthRequestUriSupplier uriSupplier = OAuth2ProviderType.providerOf(registrationId).getOAuthRequestUriSupplier();
        return uriSupplier.getUri(clientRegistration, serverProperties);
    }
}
