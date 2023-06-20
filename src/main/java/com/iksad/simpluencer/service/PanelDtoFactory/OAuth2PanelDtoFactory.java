package com.iksad.simpluencer.service.PanelDtoFactory;

import com.iksad.simpluencer.model.AgentDto;
import com.iksad.simpluencer.model.PanelDto;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;

import java.util.Map;
import java.util.Optional;

public abstract class OAuth2PanelDtoFactory {
    public static Optional<AgentDto> getPrincipal() {
        return Optional.of(SecurityContextHolder.getContext())
                .map(SecurityContext::getAuthentication)
                .map(Authentication::getPrincipal)
                .map(AgentDto.class::cast);
    }
    public static String getProvider(OAuth2UserRequest request) {
        return request.getClientRegistration().getRegistrationId();
    }
    public static Map<String, Object> getAttributes(OAuth2UserRequest request) {
        return request.getAdditionalParameters();
    }

    public abstract PanelDto newInstance(OAuth2UserRequest request);
    public abstract String getPrincipalName(OAuth2UserRequest request);
}
