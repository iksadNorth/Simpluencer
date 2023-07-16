package com.iksad.simpluencer.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.iksad.simpluencer.model.response.NoticeReadResponse;
import com.iksad.simpluencer.model.response.PanelReadResponse;
import com.iksad.simpluencer.service.NoticeService;
import com.iksad.simpluencer.service.PanelService;
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
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.SliceImpl;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.hamcrest.Matchers.containsString;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@WithMockUser
@WebMvcTest(NoticeController.class)
@MockBeansForController
@DisplayName("[NoticeController]")
class NoticeControllerTest {
    @Autowired private MockMvc mvc;
    @Autowired private ObjectMapper objectMapper;
    @InjectMocks private NoticeController noticeController;
    @MockBean NoticeService noticeService;
    @MockBean PanelService panelService;

    private MockMvcTools tools;

    @BeforeEach
    public void setup() {
        if(this.tools == null)
            this.tools = new MockMvcTools(objectMapper, mvc);
    }

    @WithMockPrincipal
    @Test @DisplayName("[notice][정상]")
    void notice() throws Exception {
        // Given
        NoticeReadResponse noticeReadResponse = NoticeReadResponse.builder()
                .id(1L)
                .image("mock_image.jpg")
                .content("mock content")
                .build();
        PageRequest pageRequest = PageRequest.of(3, 2);
        SliceImpl<NoticeReadResponse> noticeReadResponses = new SliceImpl<NoticeReadResponse>(
                List.of(noticeReadResponse,noticeReadResponse), pageRequest, true
        );

        given(noticeService.read(any(), any())).willReturn(noticeReadResponses);

        PanelReadResponse panelReadResponse = PanelReadResponse.builder()
                .id(1L)
                .frontName("mock name")
                .icon("mock_icon")
                .description("mock desc")
                .build();
        given(panelService.getPanelsOf(any())).willReturn(List.of(panelReadResponse));

        // When & Then
        String platformBtnId = String.format("id=\"platform-btn-%s\"", panelReadResponse.id());
        String panelId = String.format("panelid=\"%s\"", panelReadResponse.id());
        String panelOnclick = String.format("onclick=\"toggleHandler(&#39;platform-btn-%s&#39;)\"", panelReadResponse.id());
        String description = String.format("%s", panelReadResponse.description());

        String content = String.format("%s", noticeReadResponse.content());
        String takeNiceHistory = String.format("onclick=\"takeNoticeHistory(%s)\"", noticeReadResponse.id());

        String hasNext = String.format("const flagHasNext = %s;", noticeReadResponses.hasNext());

        tools.getView("/notice/create")
                .andExpect(view().name("notice"))
                .andExpect(content().string(containsString(platformBtnId)))
                .andExpect(content().string(containsString(panelId)))
                .andExpect(content().string(containsString(panelOnclick)))
                .andExpect(content().string(containsString(description)))

                .andExpect(content().string(containsString(content)))
                .andExpect(content().string(containsString(takeNiceHistory)))

                .andExpect(content().string(containsString(hasNext)))
                .andDo(print());
    }

    @WithMockPrincipal
    @Test @DisplayName("[notice][정상] description이 없다면 account 내용을 띄운다.")
    void noticeWithoutDescription() throws Exception {
        // Given
        NoticeReadResponse noticeReadResponse = NoticeReadResponse.builder()
                .id(1L)
                .image("mock_image.jpg")
                .content("mock content")
                .build();
        PageRequest pageRequest = PageRequest.of(3, 2);
        SliceImpl<NoticeReadResponse> noticeReadResponses = new SliceImpl<NoticeReadResponse>(
                List.of(noticeReadResponse,noticeReadResponse), pageRequest, true
        );

        given(noticeService.read(any(), any())).willReturn(noticeReadResponses);

        PanelReadResponse panelReadResponse = PanelReadResponse.builder()
                .id(1L)
                .frontName("mock name")
                .icon("mock_icon")
                .email("mock account")
                .description(null)
                .build();
        given(panelService.getPanelsOf(any())).willReturn(List.of(panelReadResponse));

        // When & Then
        String description = String.format("%s", panelReadResponse.email());

        tools.getView("/notice/create")
                .andExpect(view().name("notice"))
                .andExpect(content().string(containsString(description)))
                .andDo(print());
    }
}