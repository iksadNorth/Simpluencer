package com.iksad.simpluencer.tools;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.util.MultiValueMap;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

@RequiredArgsConstructor
public class MockMvcTools {
    private final ObjectMapper objectMapper;
    private final MockMvc mvc;

    public <T> ResultActions postJson(String url, T request) throws Exception {
        return mvc.perform(
                post(url)
                .content(objectMapper.writeValueAsBytes(request))
                .contentType(MediaType.APPLICATION_JSON)
                .with(csrf())
        );
    }

    public ResultActions getView(String url) throws Exception {
        return mvc.perform(
                MockMvcRequestBuilders.get(url)
                        .with(csrf())
        );
    }

    public ResultActions getApi(String url) throws Exception {
        return mvc.perform(
                MockMvcRequestBuilders.get(url)
                        .with(csrf())
        );
    }

    public <T> ResultActions patchJson(String url, T request) throws Exception {
        return mvc.perform(
                patch(url)
                        .content(objectMapper.writeValueAsBytes(request))
                        .contentType(MediaType.APPLICATION_JSON)
                        .with(csrf())
        );
    }

    public ResultActions delete(String url) throws Exception {
        return mvc.perform(
                        MockMvcRequestBuilders.delete(url)
                        .with(csrf())
        );
    }

    public ResultActions multipartForm(String url, MockMultipartFile multipartFile, MultiValueMap<String,String> params) throws Exception {
        return mvc.perform(
                multipart(url)
                        .file(multipartFile)
                        .params(params)
                        .contentType(MediaType.MULTIPART_FORM_DATA_VALUE)
                        .with(csrf())
        );
    }
}
