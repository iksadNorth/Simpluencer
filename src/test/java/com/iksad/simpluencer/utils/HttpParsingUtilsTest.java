package com.iksad.simpluencer.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

@Slf4j
@DisplayName("[HttpParsingUtils]")
class HttpParsingUtilsTest {
    ObjectMapper objectMapper;
    MockHttpServletRequest request;
    MockHttpServletResponse response;
    HttpParsingUtils.Arg arg;

    @BeforeEach
    public void setup() {
        this.objectMapper = new ObjectMapper();
        this.request = new MockHttpServletRequest();
        this.response = new MockHttpServletResponse();
        this.arg = HttpParsingUtils.Arg.builder()
                .objectMapper(this.objectMapper)
                .request(this.request)
                .response(this.response)
                .build();
    }

    @Test @DisplayName("[getBodyInRequest][정상]")
    void getBodyInRequest() throws IOException {
        // Given - arg가 주어진다면,
        this.request.setContent("key1=value1&key2=value2".getBytes(StandardCharsets.UTF_8));

        // When - getBodyInRequest을 실행했을 때,
        String body = HttpParsingUtils.getBodyInRequest(arg);

        // Then - response의 Body 값이 입력 값과 같다.
        assertThat(body).isEqualTo("key1=value1&key2=value2");
        log.info(body);
    }

    @Test @DisplayName("[parseFormData][정상]")
    void parseFormData() throws IOException {
        // Given - arg가 주어진다면,
        this.request.setContent("key1=value1&key2=value2".getBytes(StandardCharsets.UTF_8));

        // When - parseFormData을 실행했을 때,
        Map<String, String[]> data = HttpParsingUtils.parseFormData(arg);

        // Then - response의 Body Parse 키값이 입력 키값과 같다.
        assertThat(data).containsKey("key1");
        assertThat(data).containsKey("key2");
        for(Map.Entry<String, String[]> entry : data.entrySet()) {
            log.info("{} | {}", entry.getKey(), entry.getValue()[0]);
        }
    }

    @Test @DisplayName("[parseFormData][비정상] - 빈 값 전달.")
    void parseFormDataNull() throws IOException {
        // Given - arg가 주어진다면,
        this.request.setContent("  ".getBytes(StandardCharsets.UTF_8));

        // When - parseFormData을 실행했을 때,
        Map<String, String[]> data = HttpParsingUtils.parseFormData(arg);

        // Then - response의 Body Parse 키값이 입력 키값과 같다.
        for(Map.Entry<String, String[]> entry : data.entrySet()) {
            assertThat(entry.getValue()).isEqualTo(new String[]{null});
            log.info("{} | {}", entry.getKey(), entry.getValue()[0]);
        }
    }

    @Test @DisplayName("[recordOnResponse][정상]")
    void recordOnResponse() throws UnsupportedEncodingException {
        // Given - arg가 주어진다면,

        // When - recordOnResponse를 실행했을 때,
        Optional<IOException> result = HttpParsingUtils.recordOnResponse(arg, "Test Response");

        // Then - response의 Body 값이 입력 값과 같다.
        assertFalse(result.isPresent());
        assertEquals("\"Test Response\"", response.getContentAsString());
    }
}