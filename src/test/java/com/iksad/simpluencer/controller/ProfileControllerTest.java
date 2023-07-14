package com.iksad.simpluencer.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.iksad.simpluencer.model.response.PanelReadResponse;
import com.iksad.simpluencer.model.response.ProfileResponse;
import com.iksad.simpluencer.service.AgentService;
import com.iksad.simpluencer.tools.MockBeansForController;
import com.iksad.simpluencer.tools.MockMvcTools;
import com.iksad.simpluencer.tools.MockSecurityContextFactory.WithMockPrincipal;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.hamcrest.Matchers.containsString;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;

@WithMockPrincipal
@WebMvcTest(ProfileController.class)
@MockBeansForController
@DisplayName("[ProfileController]")
class ProfileControllerTest {
    @Autowired
    private MockMvc mvc;
    @Autowired private ObjectMapper objectMapper;
    @InjectMocks
    private ProfileController profileController;
    @MockBean
    private AgentService agentService;

    private MockMvcTools tools;

    @BeforeEach
    public void setup() {
        if(this.tools == null)
            this.tools = new MockMvcTools(objectMapper, mvc);
    }

    @Test @DisplayName("[read][정상]Panel이 주어졌을 때, 특정 문구 포함 여부.")
    void readWhenPanelGiven() throws Exception {
        // Given
        PanelReadResponse panelReadResponse = PanelReadResponse.builder()
                .id(1L)
                .link("mock link")
                .build();
        ProfileResponse profileResponse = ProfileResponse.builder()
                .panels(List.of(panelReadResponse))
                .build();
        given(agentService.readProfileById(any())).willReturn(profileResponse);

        // When & Then
        String onclickLink = String.format("<div class=\"col-md-4 clickable\" data-link=\"%s\" onclick=\"navigateTo(this.getAttribute('data-link'))\">", panelReadResponse.link());

        tools.getView("/profile/read/1")
                .andExpect(content().string(containsString(onclickLink)))
                .andDo(print());
    }

    @Test @DisplayName("[read][정상]Panel의 link값이 null값으로 주어졌을 때, 링크는 #으로 제공.")
    void readWhenPanelGivenWithoutLink() throws Exception {
        // Given
        PanelReadResponse panelReadResponse = PanelReadResponse.builder()
                .id(1L)
                .link(null)
                .build();
        ProfileResponse profileResponse = ProfileResponse.builder()
                .panels(List.of(panelReadResponse))
                .build();
        given(agentService.readProfileById(any())).willReturn(profileResponse);

        // When & Then
        String onclickLink = "<div class=\"col-md-4 clickable\" data-link=\"#\" onclick=\"navigateTo(this.getAttribute('data-link'))\">";

        tools.getView("/profile/read/1")
                .andExpect(content().string(containsString(onclickLink)))
                .andDo(print());
    }
}