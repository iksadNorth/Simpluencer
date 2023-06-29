package com.iksad.simpluencer.repository.NoticeApiRepository;

import com.iksad.simpluencer.model.WebApiNoticeCreateDto;
import com.iksad.simpluencer.repository.WebApiRepository;
import com.iksad.simpluencer.service.OAuth2TokenManager;
import org.springframework.web.client.RestTemplate;

public abstract class ProviderNoticeApiRepository extends WebApiRepository<WebApiNoticeCreateDto, Void> {
    public ProviderNoticeApiRepository(RestTemplate restTemplate, OAuth2TokenManager oAuth2TokenManager) {
        super(restTemplate, oAuth2TokenManager);
    }
}
