package com.iksad.simpluencer.service;

import com.iksad.simpluencer.config.JpaAuditingConfig;
import com.iksad.simpluencer.entity.Agent;
import com.iksad.simpluencer.entity.Panel;
import com.iksad.simpluencer.exception.ErrorType.ForbiddenUser;
import com.iksad.simpluencer.model.AgentDto;
import com.iksad.simpluencer.model.ClientRegistration;
import com.iksad.simpluencer.model.request.OAuth2AuthenticationCodeRequest;
import com.iksad.simpluencer.model.response.OAuth2AuthenticatedResponse;
import com.iksad.simpluencer.model.response.OAuth2TokenResponse;
import com.iksad.simpluencer.model.response.OAuth2UserInfoResponse;
import com.iksad.simpluencer.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class OAuth2TokenServiceImpl implements OAuth2TokenService {
    private final OAuth2AccessTokenRepository oAuth2TokenRepository;
    private final UserInfoApiRepository userInfoApiRepository;
    private final ClientRegistrationRepository clientRegistrationRepository;
    private final AccessTokenRepository accessTokenRepository;
    private final RefreshTokenRepository refreshTokenRepository;
    private final PanelRepository panelRepository;

    @Override
    public void handleAuthenticationCode(OAuth2AuthenticationCodeRequest request) {
        ClientRegistration clientRegistration = clientRegistrationRepository.findByRegistrationId(request.registrationId());

        // code를 다시 인증 서버로 보내기.
        OAuth2TokenResponse tokenResponse = oAuth2TokenRepository.getAccessTokenByCode(clientRegistration, request.code());

        // user info 정보 가져오기.
        OAuth2UserInfoResponse userInfoResponse = userInfoApiRepository.readUserInfo(clientRegistration, tokenResponse);

        // access token과 refresh token을 저장.
        OAuth2AuthenticatedResponse authenticatedResponse = OAuth2AuthenticatedResponse.builder()
                .clientRegistration(clientRegistration)
                .tokenResponse(tokenResponse)
                .userInfoResponse(userInfoResponse)
                .build();

        // 존재하지 않는 Panel이면 추가적인 작업 수행.
        Optional<Panel> optionalPanel = panelRepository.findByProviderAndPrincipalName(clientRegistration.getRegistrationId(), userInfoResponse.principalName());
        if (optionalPanel.isEmpty()) {
            // Panel에 저장하고
            this.saveIfNotExist(authenticatedResponse);

            // 토큰을 저장.
            accessTokenRepository.save(authenticatedResponse.toAccessTokenEntity());
            refreshTokenRepository.save(authenticatedResponse.toRefreshTokenEntity());
        }
    }

    private Panel saveIfNotExist(OAuth2AuthenticatedResponse authenticatedResponse) {
        Agent principal = JpaAuditingConfig.principal()
                .map(AgentDto::toEntity)
                .orElseThrow(ForbiddenUser::new);

        Panel entity = new Panel();
        entity.setAgent(principal);
        entity.setProvider(authenticatedResponse.clientRegistration().getRegistrationId());
        entity.setPrincipalName(authenticatedResponse.userInfoResponse().principalName());
        entity.setEmail(authenticatedResponse.userInfoResponse().account());
        entity.setLocation(Integer.MAX_VALUE);

        return panelRepository.save(entity);
    }
}
