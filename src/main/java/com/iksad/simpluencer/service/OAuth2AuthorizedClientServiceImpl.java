package com.iksad.simpluencer.service;

import com.iksad.simpluencer.entity.Token;
import com.iksad.simpluencer.exception.ErrorType.AuthTokenNotFoundType;
import com.iksad.simpluencer.repository.TokenRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientService;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class OAuth2AuthorizedClientServiceImpl implements OAuth2AuthorizedClientService {
    private final ClientRegistrationRepository clientRegistrationRepository;
    private final TokenRepository tokenRepository;

    @Override
    @Transactional(readOnly = true)
    public OAuth2AuthorizedClient loadAuthorizedClient(String clientRegistrationId, String principalName) {
        ClientRegistration clientRegistration = clientRegistrationRepository.findByRegistrationId(clientRegistrationId);

        Token token = tokenRepository.findByProviderAndPrincipalName(clientRegistrationId, principalName)
                .orElseThrow(() -> new AuthTokenNotFoundType(clientRegistrationId, principalName));

        return new OAuth2AuthorizedClient(
                clientRegistration,
                principalName,
                token.getOAuth2AccessToken(),
                token.getOAuth2RefreshToken()
        );
    }

    @Override
    public void saveAuthorizedClient(OAuth2AuthorizedClient authorizedClient, Authentication principal) {
        tokenRepository.save(Token.fromOAuth2AuthorizedClient(authorizedClient));
    }

    @Override
    public void removeAuthorizedClient(String clientRegistrationId, String principalName) {
        tokenRepository.deleteByProviderAndPrincipalName(clientRegistrationId, principalName);
    }
}
