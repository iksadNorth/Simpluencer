package com.iksad.simpluencer.controller;

import com.iksad.simpluencer.config.OAuth2ProviderType;
import com.iksad.simpluencer.model.request.OAuth2AuthenticationCodeRequest;
import com.iksad.simpluencer.service.OAuth2TokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import static com.iksad.simpluencer.utils.ResponseEntityUtils.getRedirectionResponse;

@RestController
@RequiredArgsConstructor
public class OAuthApiController {
    private final OAuth2TokenService oAuth2TokenService;

    @GetMapping("/login/oauth2/code/{registrationIdForName}")
    public ResponseEntity<Void> getTokens(
            @PathVariable String registrationIdForName,
            @RequestParam String code
    ) {
        OAuth2ProviderType providerType = OAuth2ProviderType.findWithProviderNameForRedirectUrl(registrationIdForName);
        OAuth2AuthenticationCodeRequest request = OAuth2AuthenticationCodeRequest.builder()
                .registrationId(providerType.name())
                .code(code)
                .build();
        oAuth2TokenService.handleAuthenticationCode(request);
        return getRedirectionResponse("/platform/create");
    }
}
