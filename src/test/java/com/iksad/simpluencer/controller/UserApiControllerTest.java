package com.iksad.simpluencer.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.iksad.simpluencer.fixture.AgentFixture;
import com.iksad.simpluencer.model.request.UserRequest;
import com.iksad.simpluencer.service.AgentService;
import com.iksad.simpluencer.tools.MockBeansForController;
import com.iksad.simpluencer.tools.MockMvcTools;
import com.iksad.simpluencer.tools.MockSecurityContextFactory.WithMockPrincipal;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Slf4j
@WithMockPrincipal
@WebMvcTest(UserApiController.class)
@MockBeansForController
@DisplayName("[UserApiController]")
class UserApiControllerTest {
    @Autowired ObjectMapper objectMapper;
    @Autowired private MockMvc mvc;
    @MockBean private AgentService agentService;

    MockMvcTools tools;

    @BeforeEach
    public void setup() {
        if(this.tools == null)
            this.tools = new MockMvcTools(objectMapper, mvc);
    }

    @Test @DisplayName("[create][정상]")
    void create() throws Exception {
        UserRequest request = AgentFixture.userRequest1();

        tools.postJson("/api/v1/user", request)
                .andExpect(status().is3xxRedirection())
                .andExpect(header().stringValues(HttpHeaders.LOCATION, "/auth/login"))
                .andDo(print());
    }

    @Test
    void resetPassword() {
    }
}