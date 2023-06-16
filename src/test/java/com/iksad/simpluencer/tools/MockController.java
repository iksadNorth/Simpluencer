package com.iksad.simpluencer.tools;

import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/test")
@RequiredArgsConstructor
public class MockController {
    @GetMapping("/error/data-integrity-violation")
    public ResponseEntity<Void> resetPassword() {
        throw new DataIntegrityViolationException("/error/data-integrity-violation");
    }
}
