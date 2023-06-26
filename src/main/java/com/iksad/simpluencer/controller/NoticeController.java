
package com.iksad.simpluencer.controller;

import com.iksad.simpluencer.model.AgentDto;
import com.iksad.simpluencer.model.response.NoticeReadResponse;
import com.iksad.simpluencer.model.response.PanelReadResponse;
import com.iksad.simpluencer.service.NoticeService;
import com.iksad.simpluencer.service.PanelService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Slice;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.data.domain.Pageable;

import java.util.List;

@Controller
@RequestMapping("/notice")
@RequiredArgsConstructor
public class NoticeController {
    private final NoticeService noticeService;
    private final PanelService panelService;

    @GetMapping("/create")
    public String notice(
            @AuthenticationPrincipal AgentDto principal,
            @PageableDefault(page = 0, size = 5) Pageable pageable,
            Model model
    ) {
        Slice<NoticeReadResponse> notices = noticeService.read(principal.id(), pageable);
        List<PanelReadResponse> panels = panelService.getPanelsOf(principal.id());

        model.addAttribute("panels", panels);
        model.addAttribute("notices", notices);
        model.addAttribute("hasNext", notices.hasNext());
        model.addAttribute("hasPrevious", notices.hasPrevious());
        return "notice";
    }
}
