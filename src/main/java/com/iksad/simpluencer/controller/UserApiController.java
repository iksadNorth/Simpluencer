package com.iksad.simpluencer.controller;

import com.iksad.simpluencer.model.request.ResetPasswordRequest;
import com.iksad.simpluencer.model.request.UserRequest;
import com.iksad.simpluencer.service.AgentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import static com.iksad.simpluencer.utils.ResponseEntityUtils.getOkResponse;
import static com.iksad.simpluencer.utils.ResponseEntityUtils.getRedirectionResponse;

@Controller
@RequestMapping("/api/v1/user")
@RequiredArgsConstructor
public class UserApiController {
    private final AgentService agentService;

    @PostMapping
    public ResponseEntity<Void> create(@RequestBody UserRequest request) {
        agentService.create(request);
        return getRedirectionResponse("/auth/login");
    }

    @PatchMapping("/unknown/password")
    public ResponseEntity<Void> resetPassword(@RequestBody ResetPasswordRequest request) {
        agentService.resetPassword(request);
        return getOkResponse();
    }
}
