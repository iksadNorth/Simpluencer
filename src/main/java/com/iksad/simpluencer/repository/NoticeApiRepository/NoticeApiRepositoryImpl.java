package com.iksad.simpluencer.repository.NoticeApiRepository;

import com.iksad.simpluencer.type.OAuth2ProviderType;
import com.iksad.simpluencer.entity.Panel;
import com.iksad.simpluencer.model.WebApiNoticeCreateDto;
import com.iksad.simpluencer.service.OAuth2TokenManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
@RequiredArgsConstructor
public class NoticeApiRepositoryImpl implements NoticeApiRepository {
    private final RestTemplate restTemplate;
    private final OAuth2TokenManager oAuth2TokenManager;

    @Override
    public void createNotice(Panel panel, WebApiNoticeCreateDto dto) {
        String provider = panel.getProvider();
        String principalName = panel.getPrincipalName();

        OAuth2ProviderType providerType = OAuth2ProviderType.valueOf(provider);
        ProviderNoticeApiRepository apiRepository = providerType.getConstructorNoticeApiRepository()
                .apply(restTemplate, oAuth2TokenManager);

        apiRepository.fetch(provider, principalName, dto);
    }
}
