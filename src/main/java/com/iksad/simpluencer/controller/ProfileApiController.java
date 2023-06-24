package com.iksad.simpluencer.controller;

import com.iksad.simpluencer.model.AgentDto;
import com.iksad.simpluencer.model.request.ProfileUpdateRequest;
import com.iksad.simpluencer.service.AgentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.iksad.simpluencer.utils.ResponseEntityUtils.getOkResponse;

@RestController
@RequestMapping("/api/v1/profile")
@RequiredArgsConstructor
public class ProfileApiController {
    private final AgentService agentService;

    @PostMapping
    public ResponseEntity<Void> update(
            @AuthenticationPrincipal AgentDto principal,
            @ModelAttribute ProfileUpdateRequest request
            ) {
        agentService.update(principal, request);
        return getOkResponse();
    }
}
