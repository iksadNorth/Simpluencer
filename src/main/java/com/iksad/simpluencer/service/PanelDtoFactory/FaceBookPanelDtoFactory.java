package com.iksad.simpluencer.service.PanelDtoFactory;

import com.iksad.simpluencer.exception.ErrorType.ForbiddenUser;
import com.iksad.simpluencer.model.AgentDto;
import com.iksad.simpluencer.model.PanelDto;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;

import java.util.Map;

public class FaceBookPanelDtoFactory extends OAuth2PanelDtoFactory {
    @Override
    public PanelDto newInstance(OAuth2UserRequest request) {
        AgentDto agentDto = this.getPrincipal()
                .orElseThrow(() -> new ForbiddenUser());

        return PanelDto.builder()
                .agentDto(agentDto)

                .provider(this.getProvider(request))
                .principalName(this.getPrincipalName(request))

                .attributes(this.getAttributes(request))
                .build();
    }

    @Override
    public String getPrincipalName(OAuth2UserRequest request) {
        Map<String, Object> parameters = request.getAdditionalParameters();
        return (String) parameters.getOrDefault("id", null);
    }
}
