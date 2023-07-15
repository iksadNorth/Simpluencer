package com.iksad.simpluencer.service;

import com.iksad.simpluencer.OAuth2.OAuthRequestUriSupplier;
import com.iksad.simpluencer.Properties.ServerProperties;
import com.iksad.simpluencer.model.ClientRegistration;
import com.iksad.simpluencer.model.PlatformTypeDto;
import com.iksad.simpluencer.repository.ClientRegistrationRepository;
import com.iksad.simpluencer.type.OAuth2ProviderType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Stream;

@Service
@Transactional
@RequiredArgsConstructor
public class PlatformServiceImpl implements PlatformService {
    private final ClientRegistrationRepository clientRegistrationRepository;
    private final ServerProperties serverProperties;
    private final OAuthRequestUriSupplier oAuthRequestUriSupplier;

    public List<PlatformTypeDto> getPlatforms() {
        return Stream.of(OAuth2ProviderType.values())
                .map(platformType -> {
                    ClientRegistration clientRegistration = clientRegistrationRepository.findByRegistrationId(platformType.getProvider());
                    return PlatformTypeDto.of(platformType, oAuthRequestUriSupplier.getUri(clientRegistration, serverProperties));
                }).toList();
    }
}
