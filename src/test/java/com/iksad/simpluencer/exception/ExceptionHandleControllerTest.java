package com.iksad.simpluencer.exception;

import com.iksad.simpluencer.tools.MockBeansForController;
import com.iksad.simpluencer.tools.MockController;
import com.iksad.simpluencer.tools.MockSecurityContextFactory.WithMockPrincipal;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@Slf4j
@WithMockPrincipal
@WebMvcTest(MockController.class)
@MockBeansForController
@DisplayName("[ExceptionHandleController]")
class ExceptionHandleControllerTest {
    @Autowired private MockMvc mvc;

    @Test @DisplayName("[handleDataIntegrityViolationException][정상]")
    void handleDataIntegrityViolationException() throws Exception {
        mvc.perform(get("/test/error/data-integrity-violation"))
                .andExpect(status().is4xxClientError())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.errorMessage").exists())
                .andDo(print());
    }
}