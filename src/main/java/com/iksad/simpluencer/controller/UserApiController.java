package com.iksad.simpluencer.controller;

import com.iksad.simpluencer.model.request.ResetPasswordRequest;
import com.iksad.simpluencer.model.request.UserRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/api/v1/user")
@RequiredArgsConstructor
public class UserApiController {

    @PostMapping
    public ResponseEntity<Void> create(@RequestBody UserRequest request) {
        return null;
    }

    @PatchMapping("/unknown/password")
    public ResponseEntity<Void> resetPassword(@RequestBody ResetPasswordRequest request) {
        return null;
    }
}
