package com.iksad.simpluencer.exception;

import com.iksad.simpluencer.exception.ExceptionParserFactory.ExceptionParserFactory;
import com.iksad.simpluencer.tools.MockController;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@Slf4j
@WithMockUser
@WebMvcTest(MockController.class)
@DisplayName("[ExceptionHandleController]")
class ExceptionHandleControllerTest {
    @Autowired private MockMvc mvc;
    @MockBean ExceptionParserFactory exceptionParserFactory;

    @Test @DisplayName("[handleDataIntegrityViolationException][정상]")
    void handleDataIntegrityViolationException() throws Exception {
        mvc.perform(get("/test/error/data-integrity-violation"))
                .andExpect(status().is4xxClientError())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.errorMessage").exists())
                .andDo(print());
    }
}