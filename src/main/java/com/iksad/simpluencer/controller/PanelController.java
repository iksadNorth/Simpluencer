package com.iksad.simpluencer.controller;

import com.iksad.simpluencer.model.AgentDto;
import com.iksad.simpluencer.model.PlatformTypeDto;
import com.iksad.simpluencer.model.response.PanelReadResponse;
import com.iksad.simpluencer.model.response.ProfileResponse;
import com.iksad.simpluencer.service.PanelService;
import com.iksad.simpluencer.service.PlatformService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/platform")
@RequiredArgsConstructor
public class PanelController {
    private final PlatformService platformService;
    private final PanelService panelService;

    @GetMapping("/create")
    public String create(@AuthenticationPrincipal AgentDto principal, Model model) {
        ProfileResponse profileResponse = ProfileResponse.of(principal);
        model.addAttribute("profile", profileResponse);

        List<PlatformTypeDto> platforms = platformService.getPlatforms();
        model.addAttribute("platforms", platforms);

        List<PanelReadResponse> panels = panelService.getPanelsOf(principal.id());
        model.addAttribute("panels", panels);
        return "platform_register";
    }
}
