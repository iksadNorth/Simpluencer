package com.iksad.simpluencer.OAuth2;

import com.iksad.simpluencer.entity.Panel;
import com.iksad.simpluencer.model.WebApiNoticeCreateDto;
import com.iksad.simpluencer.service.OAuth2TokenManager;
import lombok.Builder;
import org.springframework.web.client.RestTemplate;

public interface NoticeApiRepository {
    @Builder
    public record Args(
            RestTemplate restTemplate,
            OAuth2TokenManager oAuth2TokenManager
    ) {}
    void createNotice(Panel panel, WebApiNoticeCreateDto dto, Args args);
}
