package com.iksad.simpluencer.service;

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

    public List<PlatformTypeDto> getPlatforms() {
        return Stream.of(OAuth2ProviderType.values())
                .map(platformType -> PlatformTypeDto.of(platformType, clientRegistrationRepository))
                .toList();
    }
}
