package com.iksad.simpluencer.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.iksad.simpluencer.model.request.ProfileUpdateRequest;
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
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import static com.iksad.simpluencer.tools.ImageTools.loadImage;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WithMockPrincipal
@WebMvcTest(ProfileApiController.class)
@MockBeansForController
@MockBean(AgentService.class)
@DisplayName("[ProfileApiController]")
class ProfileApiControllerTest {
    @Autowired private MockMvc mvc;
    @Autowired private ObjectMapper objectMapper;
    @InjectMocks private ProfileApiController profileApiController;

    private MockMvcTools tools;

    @BeforeEach
    public void setup() {
        if(this.tools == null)
            this.tools = new MockMvcTools(objectMapper, mvc);
    }

    @Test @DisplayName("[update][정상]")
    void update() throws Exception {
        // Given
        byte[] image = loadImage("https://cdn.pixabay.com/photo/2023/05/11/13/11/man-7986401_1280.jpg");
        String originalName = "mock original name.jpg";
        MockMultipartFile multipartFile = new MockMultipartFile("mock name", originalName, null, image);

        ProfileUpdateRequest request = ProfileUpdateRequest.builder()
                .nickname("mock nickname")
                .introduction("mock introduction")
                .locations(new Long[]{2L, 1L, 4L, 3L, 5L})
                .image(multipartFile)
                .build();

        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("nickname", request.nickname());
        params.add("introduction", request.introduction());
        for (Long location : request.locations()) {
            params.add("locations", String.valueOf(location));
        }

        // When & Then
        tools.multipartForm("/api/v1/profile", (MockMultipartFile) request.image(), params)
                .andExpect(status().isOk())
                .andDo(print());
    }
}