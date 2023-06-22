package com.iksad.simpluencer.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.iksad.simpluencer.exception.ExceptionParserFactory.ExceptionParserFactory;
import com.iksad.simpluencer.model.request.PanelUpdateRequest;
import com.iksad.simpluencer.service.PanelService;
import com.iksad.simpluencer.tools.MockMvcTools;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WithMockUser
@WebMvcTest(PanelApiController.class)
@DisplayName("[PanelApiController]")
class PanelApiControllerTest {
    @Autowired private MockMvc mvc;
    @Autowired private ObjectMapper objectMapper;
    @InjectMocks private PanelApiController panelApiController;
    @MockBean private PanelService panelService;
    @MockBean private ExceptionParserFactory exceptionParserFactory;

    private MockMvcTools tools;

    @BeforeEach
    public void setup() {
        if(this.tools == null)
            this.tools = new MockMvcTools(objectMapper, mvc);
    }

    @Test @DisplayName("[update][정상]")
    void update() throws Exception {
        PanelUpdateRequest request = PanelUpdateRequest.builder()
                .description("수정된 세부 정보.")
                .build();

        tools.patchJson("/api/v1/panel/1", request)
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test @DisplayName("[delete][정상]")
    void delete() throws Exception {
        tools.delete("/api/v1/panel/1")
                .andExpect(status().isOk())
                .andDo(print());
    }
}