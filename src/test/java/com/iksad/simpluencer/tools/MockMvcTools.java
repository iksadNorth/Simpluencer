package com.iksad.simpluencer.tools;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@RequiredArgsConstructor
public class MockMvcTools {
    private final ObjectMapper objectMapper;
    private final MockMvc mvc;

    public <T> ResultActions postJson(String url, T request) throws Exception {
        return mvc.perform(
                post(url)
                .content(objectMapper.writeValueAsBytes(request))
                .contentType(MediaType.APPLICATION_JSON)
                .with(csrf())
        );
    }
}
