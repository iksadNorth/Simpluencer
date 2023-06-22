package com.iksad.simpluencer.tools.MockSecurityContextFactory;

import com.iksad.simpluencer.fixture.AgentFixture;
import com.iksad.simpluencer.model.AgentDto;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.test.context.support.WithSecurityContextFactory;

public class WithSecurityContextFactoryImpl implements WithSecurityContextFactory<WithMockPrincipal> {
    @Override
    public SecurityContext createSecurityContext(WithMockPrincipal annotation) {
        SecurityContext context = SecurityContextHolder.createEmptyContext();

        AgentDto principal = AgentFixture.principal();
        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(
                principal, "", principal.getAuthorities()
        );

        context.setAuthentication(token);
        return context;
    }
}
