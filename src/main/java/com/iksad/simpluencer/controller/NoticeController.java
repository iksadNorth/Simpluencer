
package com.iksad.simpluencer.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/notice")
@RequiredArgsConstructor
public class NoticeController {

    @GetMapping
    public String notice(@RequestParam(defaultValue = "0") int page, Model model) {
        model.addAttribute("panels", null);
        model.addAttribute("notices", null);
        model.addAttribute("hasNext", null);
        model.addAttribute("hasPrevious", null);
        return "notice";
    }
}
