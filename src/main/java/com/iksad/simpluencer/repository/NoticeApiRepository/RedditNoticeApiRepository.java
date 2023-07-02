package com.iksad.simpluencer.repository.NoticeApiRepository;

import com.iksad.simpluencer.model.WebApiNoticeCreateDto;
import com.iksad.simpluencer.service.OAuth2TokenManager;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

public class RedditNoticeApiRepository extends ProviderNoticeApiRepository {
    public RedditNoticeApiRepository(RestTemplate restTemplate, OAuth2TokenManager oAuth2TokenManager) {
        super(restTemplate, oAuth2TokenManager);
    }

    @Override
    public HttpMethod getMethod() {
        return HttpMethod.POST;
    }

    @Override
    public String getUri() {
        return "https://oauth.reddit.com/api/submit";
    }

    @Override
    public void addHeaders(HttpHeaders headers) {
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
    }

    @Override
    public MultiValueMap<String, Object> fromRequest(WebApiNoticeCreateDto dto) {
        MultiValueMap<String, Object> params = new LinkedMultiValueMap<>();
        params.add("sr", "simpluencer_notice");
        params.add("title", "공지입니다!!");
        params.add("text", dto.content());
        params.add("url", dto.getRealUri());
        params.add("kind", "image");
        params.add("kind", "self");
        return params;
    }

    @Override
    public Void toResponse(Map<String, Object> response) {
        return null;
    }
}
