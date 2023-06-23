package com.iksad.simpluencer.controller;

import com.iksad.simpluencer.model.response.ProfileResponse;
import com.iksad.simpluencer.service.AgentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/profile")
@RequiredArgsConstructor
public class ProfileController {
    private final AgentService agentService;

    @GetMapping("/read/{id}")
    public String read(@PathVariable Long id, Model model) {
        ProfileResponse profileResponse = agentService.readProfileById(id);
        model.addAttribute("profile", profileResponse);
        return "profile_read";
    }
}
