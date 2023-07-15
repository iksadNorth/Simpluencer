package com.iksad.simpluencer.OAuth2;

import com.iksad.simpluencer.Properties.ServerProperties;
import com.iksad.simpluencer.model.ClientRegistration;

public interface OAuthRequestUriSupplier {
    String getUri(ClientRegistration clientRegistration, ServerProperties serverProperties);
}
