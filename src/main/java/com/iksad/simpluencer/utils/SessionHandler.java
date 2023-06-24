package com.iksad.simpluencer.utils;

import com.iksad.simpluencer.model.AgentDto;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

public class SessionHandler {
    public static void updateContext(AgentDto principal) {
        Authentication token = new UsernamePasswordAuthenticationToken(principal, "", principal.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(token);
    }
}
