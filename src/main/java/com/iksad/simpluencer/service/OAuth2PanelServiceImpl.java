package com.iksad.simpluencer.service;

import com.iksad.simpluencer.entity.Panel;
import com.iksad.simpluencer.model.AgentDto;
import com.iksad.simpluencer.model.PanelDto;
import com.iksad.simpluencer.repository.PanelRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class OAuth2PanelServiceImpl implements OAuth2PanelService {
    private final PanelRepository panelRepository;
    private final OAuth2UserService<OAuth2UserRequest, OAuth2User> oAuth2UserService = new DefaultOAuth2UserService();

    @Override
    public PanelDto loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2User oAuth2User = oAuth2UserService.loadUser(userRequest);
        Panel entity = castRequestToEntity(userRequest, oAuth2User);
        Panel saved = loadOrSave(entity);

        return PanelDto.fromEntity(saved)
                .toBuilder()
                .agentDto(AgentDto.fromEntity(saved.getAgent()))
                .attributes(oAuth2User.getAttributes())
                .build();
    }

    public Panel loadOrSave(Panel entity) {
        return panelRepository.findByProviderAndPrincipalName(
                entity.getProvider(), entity.getPrincipalName()
        ).orElseGet(() -> panelRepository.save(entity));
    }

    public static Panel castRequestToEntity(OAuth2UserRequest request, OAuth2User oAuth2User) {
        Panel entity = new Panel();
        entity.setProvider(getProvider(request));
        entity.setPrincipalName(oAuth2User.getName());
        entity.setEmail(oAuth2User.getAttribute("email"));
        return entity;
    }

    public static String getProvider(OAuth2UserRequest request) {
        return request.getClientRegistration().getRegistrationId();
    }
}
