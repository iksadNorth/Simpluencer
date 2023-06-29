package com.iksad.simpluencer.model;

import com.iksad.simpluencer.entity.Panel;
import lombok.Builder;

import java.util.Map;

@Builder(toBuilder = true)
public record PanelDto(
        AgentDto agentDto,
        String provider,
        String principalName,
        String description,
        int location,
        Map<String, Object> attributes
    ) {

    public static PanelDto fromEntity(Panel entity) {
        return PanelDto.builder()
                .provider(entity.getProvider())
                .principalName(entity.getPrincipalName())
                .description(entity.getDescription())
                .location(entity.getLocation())
                .build();
    }

    public Panel toEntity() {
        assert this.agentDto != null;

        Panel entity = new Panel();
        entity.setAgent(this.agentDto.toEntity());
        entity.setProvider(this.provider);
        entity.setPrincipalName(this.principalName);
        entity.setDescription(this.description);
        entity.setLocation(this.location);
        return entity;
    }
}
