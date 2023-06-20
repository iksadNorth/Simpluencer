package com.iksad.simpluencer.service.PanelDtoFactory;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.iksad.simpluencer.exception.ErrorType.ForbiddenUser;
import com.iksad.simpluencer.model.AgentDto;
import com.iksad.simpluencer.model.PanelDto;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;

import java.io.IOException;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

public class GooglePanelDtoFactory extends OAuth2PanelDtoFactory {
    private static final Base64.Decoder decoder = Base64.getDecoder();
    private static final ObjectMapper objectMapper = new ObjectMapper();
    private static final TypeReference<Map<String, Object>> typeRef = new TypeReference<>() {};

    @Override
    public PanelDto newInstance(OAuth2UserRequest request) {
        Map<String, Object> parameters = request.getAdditionalParameters();
        parameters = decode(parameters);

        AgentDto agentDto = this.getPrincipal()
                .orElseThrow(() -> new ForbiddenUser());

        return PanelDto.builder()
                .agentDto(agentDto)

                .provider(this.getProvider(request))
                .principalName(this.getPrincipalName(parameters))

                .attributes(parameters)
                .build();
    }

    private String getPrincipalName(Map<String, Object> parameters) {
        return (String) parameters.getOrDefault("sub", null);
    }

    @Override
    public String getPrincipalName(OAuth2UserRequest request) {
        Map<String, Object> parameters = request.getAdditionalParameters();
        return (String) parameters.getOrDefault("sub", null);
    }

    public static Map<String, Object> decode(Map<String, Object> attrs) {
        Map<String, Object> result = new HashMap<>(attrs);
        String idToken;

        try {
            idToken = (String) result.get("id_token");
        } catch (NullPointerException e) { return attrs;}

        String[] idTokenSplit = idToken.split("\\.");
        byte[] payload = decoder.decode(idTokenSplit[1]);

        try {
            Map<String, Object> idTokenDecoded = objectMapper.readValue(payload, typeRef);
            result.putAll(idTokenDecoded);
        } catch (IOException ignored) {}
        return result;
    }
}
