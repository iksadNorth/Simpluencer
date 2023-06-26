package com.iksad.simpluencer.controller;

import com.iksad.simpluencer.model.AgentDto;
import com.iksad.simpluencer.model.request.NoticeCreateRequest;
import com.iksad.simpluencer.model.response.NoticeReadResponse;
import com.iksad.simpluencer.service.NoticeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import static com.iksad.simpluencer.utils.ResponseEntityUtils.getOkResponse;

@RestController
@RequestMapping("/api/v1/notice")
@RequiredArgsConstructor
public class NoticeApiController {
    private final NoticeService noticeService;

    @PostMapping
    public ResponseEntity<Void> create(
            @AuthenticationPrincipal AgentDto principal,
            @ModelAttribute NoticeCreateRequest request
    ) {
        noticeService.create(principal.id(), request);
        return getOkResponse();
    }

    @GetMapping("/{id}")
    public NoticeReadResponse read(
            @PathVariable Long id
    ) {
        return noticeService.readById(id);
    }
}
