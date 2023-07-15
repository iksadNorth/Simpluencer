package com.iksad.simpluencer.OAuth2.reddit;

import com.iksad.simpluencer.OAuth2.NoticeApiRepository;
import com.iksad.simpluencer.entity.Panel;
import com.iksad.simpluencer.model.WebApiNoticeCreateDto;
import com.iksad.simpluencer.service.OAuth2TokenManager;
import com.iksad.simpluencer.type.OAuth2ProviderType;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.util.Map;

public class NoticeApiRepositoryImpl implements NoticeApiRepository {
    private final ParameterizedTypeReference<Map<String, Object>> parameterizedTypeReference = new ParameterizedTypeReference<>() {};

    private HttpHeaders getHeaders(String providerName, String principalName, OAuth2TokenManager oAuth2TokenManager) {
        String accessToken = oAuth2TokenManager.getAccessToken(providerName, principalName);

        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(accessToken);
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        return headers;
    }

    private MultiValueMap<String, Object> fromRequest(WebApiNoticeCreateDto dto) {
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
    public void createNotice(Panel panel, WebApiNoticeCreateDto dto, Args args) {
        String providerName = OAuth2ProviderType.REDDIT.getProvider();
        HttpHeaders headers = getHeaders(providerName, panel.getPrincipalName(), args.oAuth2TokenManager());

        HttpEntity<Object> httpEntity = new HttpEntity<>(fromRequest(dto), headers);
        args.restTemplate().exchange(
                "https://oauth.reddit.com/api/submit",
                HttpMethod.POST,
                httpEntity, this.parameterizedTypeReference
        );
    }
}
