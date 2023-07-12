package com.iksad.simpluencer.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.iksad.simpluencer.model.PlatformTypeDto;
import com.iksad.simpluencer.model.response.PanelReadResponse;
import com.iksad.simpluencer.service.PanelService;
import com.iksad.simpluencer.service.PlatformService;
import com.iksad.simpluencer.tools.MockBeansForController;
import com.iksad.simpluencer.tools.MockMvcTools;
import com.iksad.simpluencer.tools.MockSecurityContextFactory.WithMockPrincipal;
import com.iksad.simpluencer.type.OAuth2ProviderType;
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
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WithMockPrincipal
@WebMvcTest(PanelController.class)
@MockBeansForController
@DisplayName("[PanelController]")
class PanelControllerTest {
    @Autowired
    private MockMvc mvc;
    @Autowired private ObjectMapper objectMapper;
    @InjectMocks private PanelController panelController;
    @MockBean private PlatformService platformService;
    @MockBean private PanelService panelService;

    private MockMvcTools tools;

    @BeforeEach
    public void setup() {
        if(this.tools == null)
            this.tools = new MockMvcTools(objectMapper, mvc);
    }

    @Test @DisplayName("[create][정상]")
    void create() throws Exception {
        tools.getView("/platform/create")
                .andExpect(view().name("platform_register"))
                .andExpect(model().attributeExists("platforms", "panels"))
                .andDo(print());
    }

    @Test @DisplayName("[create][정상]Platform이 google로 주어졌을 때, 특정 태그 포함 여부.")
    void createWhenPlatformGiven() throws Exception {
        // Given
        PlatformTypeDto google = PlatformTypeDto.of(OAuth2ProviderType.GOOGLE);
        given(platformService.getPlatforms()).willReturn(List.of(google));

        given(panelService.getPanelsOf(any())).willReturn(List.of());

        // When & Then
        String provider = String.format("id=\"registration_platform_%s\"", google.provider());
        String icon = String.format("src=\"/img/%s", google.icon());
        String frontName = google.frontName();

        tools.getView("/platform/create")
                .andExpect(content().string(containsString(provider)))
                .andExpect(content().string(containsString(icon)))
                .andExpect(content().string(containsString(frontName)))
                .andDo(print());
    }

    @Test @DisplayName("[create][정상]Panel이 google로 주어졌을 때, 특정 태그 포함 여부.")
    void createWhenPanelGiven() throws Exception {
        // Given
        given(platformService.getPlatforms()).willReturn(List.of());

        PanelReadResponse response = PanelReadResponse.builder()
                .id(1L)
                .frontName("mock name")
                .icon("mock_icon")
                .description("mock desc")
                .build();
        given(panelService.getPanelsOf(any())).willReturn(List.of(response));

        // When & Then
        String id = String.format("id=\"card_platform_%s\"", response.id());
        String icon = String.format("xlink:href=\"/img/%s\"", response.icon());
        String frontName = response.frontName();
        String label = String.format("id=\"card_platform_%s_label\"", response.id());
        String description = response.description();
        String text = String.format("id=\"card_platform_%s_text\"", response.id());

        String clickWillUpdatePanel = String.format("clickWillUpdatePanel_%s", response.id());
        String clickGetUpdatePanel = String.format("clickGetUpdatePanel_%s", response.id());
        String fetchPatch = String.format("/api/v1/panel/%s", response.id());
        String clickDeletePanel = String.format("clickDeletePanel_%s", response.id());

        tools.getView("/platform/create")
                .andExpect(content().string(containsString(id)))
                .andExpect(content().string(containsString(icon)))
                .andExpect(content().string(containsString(frontName)))
                .andExpect(content().string(containsString(label)))
                .andExpect(content().string(containsString(description)))
                .andExpect(content().string(containsString(text)))

                .andExpect(content().string(containsString(clickWillUpdatePanel)))
                .andExpect(content().string(containsString(clickGetUpdatePanel)))
                .andExpect(content().string(containsString(fetchPatch)))
                .andExpect(content().string(containsString(clickDeletePanel)))
                .andDo(print());
    }
}