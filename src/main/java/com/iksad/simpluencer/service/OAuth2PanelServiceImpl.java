package com.iksad.simpluencer.service;

import com.iksad.simpluencer.entity.Panel;
import com.iksad.simpluencer.model.AgentDto;
import com.iksad.simpluencer.model.PanelDto;
import com.iksad.simpluencer.repository.PanelRepository;
import com.iksad.simpluencer.service.PanelDtoFactory.OAuth2PanelDtoFactory;
import com.iksad.simpluencer.type.PlatformType;
import lombok.RequiredArgsConstructor;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class OAuth2PanelServiceImpl implements OAuth2PanelService {
    private final PanelRepository panelRepository;

    @Override
    public PanelDto loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        String provider = userRequest.getClientRegistration().getRegistrationId();
        PlatformType platformType = PlatformType.providerOf(provider);
        OAuth2PanelDtoFactory oAuth2PanelDtoFactory = platformType.getOAuth2PanelDtoFactory();

        PanelDto panelDtoFromOAuth2 = oAuth2PanelDtoFactory.newInstance(userRequest);

        Panel panel = panelRepository.findByProviderAndPrincipalName(
                panelDtoFromOAuth2.provider(), panelDtoFromOAuth2.principalName()
        ).orElseGet(() -> panelRepository.save(panelDtoFromOAuth2.toEntity()));

        return PanelDto.fromEntity(panel)
                .toBuilder()
                .agentDto(AgentDto.fromEntity(panel.getAgent()))
                .build();
    }
}
