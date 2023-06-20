package com.iksad.simpluencer.exception.ErrorType;

import com.iksad.simpluencer.exception.SimpluencerException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@RequiredArgsConstructor
public class AuthTokenNotFoundType extends SimpluencerException {
    private final String clientRegistrationId;
    private final String principalName;

    @Override
    public HttpStatus getHttpStatus() {
        return HttpStatus.NOT_FOUND;
    }

    @Override
    public String getMessageForClient() {
        return "해당 플랫폼의 계정으로는 등록되지 않습니다.";
    }

    public String getMessageForServer() {
        return String.format(
                "존재하지 않는 인증 토큰을 요구함.\nPlatform|%s\nId_in_this_platform|%s"
                , this.clientRegistrationId, this.principalName
        );
    }
}
