package com.iksad.simpluencer.model;

import com.iksad.simpluencer.entity.Agent;
import com.iksad.simpluencer.entity.RoleOfAgent;
import com.iksad.simpluencer.type.RoleType;
import com.iksad.simpluencer.utils.TypeTransformUtils;
import lombok.Builder;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.function.Function;

@Builder(toBuilder = true)
public record AgentDto(
        Long id,
        LocalDateTime createdAt,
        String email,
        String password,
        String nickname,
        Collection<RoleType> roles,
        Collection<PanelDto> panelDtos
) implements UserDetails {
    public static AgentDto fromEntity(Agent entity) {
        return AgentDto.builder()
                .id(entity.getId())
                .createdAt(entity.getCreatedAt())

                .email(entity.getEmail())
                .password(entity.getPassword())
                .nickname(entity.getNickname())

                .roles(TypeTransformUtils.map(entity.getRoles(), RoleOfAgent::getRole))
                .build();
    }

    public Agent toEntity() {
        Agent entity = new Agent();
        entity.setId(this.id);
        entity.setCreatedAt(this.createdAt);
        entity.setEmail(this.email);
        entity.setPassword(this.password);
        entity.setNickname(this.nickname);
        return entity;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return TypeTransformUtils.map(
                this.roles,
                Function.<RoleType>identity()
                        .andThen(RoleType::getDbName)
                        .andThen(SimpleGrantedAuthority::new)
        );
    }

    @Override
    public String getUsername() { return this.email; }
    @Override
    public String getPassword() { return this.password; }

    @Override
    public boolean isAccountNonExpired() { return true; }
    @Override
    public boolean isAccountNonLocked() { return true; }
    @Override
    public boolean isCredentialsNonExpired() { return true; }
    @Override
    public boolean isEnabled() { return true; }
}
