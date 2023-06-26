package com.iksad.simpluencer.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.iksad.simpluencer.model.request.NoticeCreateRequest;
import com.iksad.simpluencer.service.NoticeService;
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
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import static com.iksad.simpluencer.tools.ImageTools.loadImage;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WithMockUser
@WebMvcTest(NoticeApiController.class)
@MockBeansForController
@MockBean(NoticeService.class)
@DisplayName("[NoticeApiController]")
class NoticeApiControllerTest {
    @Autowired private MockMvc mvc;
    @Autowired private ObjectMapper objectMapper;
    @InjectMocks private PanelApiController panelApiController;

    private MockMvcTools tools;

    @BeforeEach
    public void setup() {
        if(this.tools == null)
            this.tools = new MockMvcTools(objectMapper, mvc);
    }

    @WithMockPrincipal
    @Test @DisplayName("[create][정상]")
    void create() throws Exception {
        // Given
        byte[] image = loadImage("https://cdn.pixabay.com/photo/2023/05/11/13/11/man-7986401_1280.jpg");
        String originalName = "mock original name.jpg";
        MockMultipartFile multipartFile = new MockMultipartFile("mock name", originalName, null, image);

        NoticeCreateRequest request = NoticeCreateRequest.builder()
                .content("mock content")
                .platforms(new Long[]{2L, 1L, 4L, 3L, 5L})
                .image(multipartFile)
                .build();

        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("content", request.content());
        for (Long location : request.platforms()) {
            params.add("platforms", String.valueOf(location));
        }

        // When & Then
        tools.multipartForm("/api/v1/notice", (MockMultipartFile) request.image(), params)
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test @DisplayName("[read][정상]")
    void read() throws Exception {
        tools.getApi("/api/v1/notice/1")
                .andExpect(status().isOk())
                .andDo(print());
    }
}