package com.iksad.simpluencer.config;

import com.iksad.simpluencer.entity.Agent;
import com.iksad.simpluencer.model.AgentDto;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Optional;

@Configuration
public class JpaAuditingConfig {
    @Bean
    public AuditorAware<Agent> auditorAware() {
        return () -> JpaAuditingConfig.principal()
                .map(AgentDto::toEntity);
    }

    public static Optional<AgentDto> principal() {
        return Optional.of(SecurityContextHolder.getContext())
                .map(SecurityContext::getAuthentication)
                .filter(Authentication::isAuthenticated)
                .map(Authentication::getPrincipal)
                .map(AgentDto.class::cast);
    }
}
