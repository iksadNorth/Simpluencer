package com.iksad.simpluencer.model;

import com.iksad.simpluencer.entity.Panel;
import lombok.Builder;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.Collection;
import java.util.Map;

@Builder(toBuilder = true)
public record PanelDto(
        AgentDto agentDto,
        String provider,
        String principalName,
        String description,
        int location,
        Map<String, Object> attributes
    ) implements OAuth2User {

    public static PanelDto fromEntity(Panel entity) {
        return PanelDto.builder()
                .provider(entity.getProvider())
                .principalName(entity.getPrincipalName())
                .description(entity.getDescription())
                .location(entity.getLocation())
                .build();
    }

    @Override
    public String getName() { return this.principalName; }
    @Override
    public Map<String, Object> getAttributes() { return this.attributes; }
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() { return this.agentDto.getAuthorities(); }

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
