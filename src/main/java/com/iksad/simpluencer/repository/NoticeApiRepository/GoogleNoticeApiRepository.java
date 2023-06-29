package com.iksad.simpluencer.repository.NoticeApiRepository;

import com.iksad.simpluencer.model.WebApiNoticeCreateDto;
import com.iksad.simpluencer.service.OAuth2TokenManager;
import org.springframework.http.HttpMethod;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

public class GoogleNoticeApiRepository extends ProviderNoticeApiRepository {
    public GoogleNoticeApiRepository(RestTemplate restTemplate, OAuth2TokenManager oAuth2TokenManager) {
        super(restTemplate, oAuth2TokenManager);
    }

    @Override
    public HttpMethod getMethod() {
        return null;
    }

    @Override
    public String getUri() {
        return null;
    }

    @Override
    public Map<String, Object> fromRequest(WebApiNoticeCreateDto webApiNoticeCreateDto) {
        return null;
    }

    @Override
    public Void toResponse(Map<String, Object> response) {
        return null;
    }
}
