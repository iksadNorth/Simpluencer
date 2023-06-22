package com.iksad.simpluencer.service;

import com.iksad.simpluencer.model.PlatformTypeDto;
import com.iksad.simpluencer.type.PlatformType;
import lombok.RequiredArgsConstructor;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
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
        return Stream.of(PlatformType.values())
                .map(platformType -> PlatformTypeDto.of(platformType, clientRegistrationRepository))
                .toList();
    }
}
