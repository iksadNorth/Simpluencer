package com.iksad.simpluencer.config;

import com.iksad.simpluencer.model.AgentDto;
import org.springframework.context.annotation.Configuration;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

import java.util.concurrent.atomic.AtomicBoolean;

@Configuration
@ControllerAdvice
public class ThymeleafConfig {
    @ModelAttribute
    public void addPrincipalToModel(Model model) {
        AtomicBoolean isAuthenticated = new AtomicBoolean(true);

        AgentDto principal = JpaAuditingConfig.principal().orElseGet(() -> {
            isAuthenticated.set(false);
            return null;
        });

        model.addAttribute("principal", principal);
        model.addAttribute("isAuthenticated", isAuthenticated.get());
    }
}
