package com.iksad.simpluencer.model.request;

import com.iksad.simpluencer.entity.Agent;
import com.iksad.simpluencer.entity.RoleOfAgent;
import com.iksad.simpluencer.type.RoleType;
import lombok.Builder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Builder(toBuilder = true)
public record UserRequest(
        String email,
        String password,
        String nickname
) {
    public Agent toEntity(PasswordEncoder passwordEncoder) {
        Agent entity = new Agent();
        entity.setEmail(this.email);
        entity.setPassword(
                passwordEncoder.encode(this.password)
        );
        entity.setNickname(this.nickname);
        entity.addRole(RoleOfAgent.of(RoleType.USER));
        return entity;
    }
}
