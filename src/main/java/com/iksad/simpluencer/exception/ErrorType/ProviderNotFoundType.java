package com.iksad.simpluencer.exception.ErrorType;

import com.iksad.simpluencer.exception.SimpluencerException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@RequiredArgsConstructor
public class ProviderNotFoundType extends SimpluencerException {
    private final String provider;

    @Override
    public HttpStatus getHttpStatus() {
        return HttpStatus.NOT_FOUND;
    }

    @Override
    public String getMessageForClient() {
        return "인증 중 문제가 발생했습니다.";
    }

    public String getMessageForServer() {
        return String.format("옳바르지 못한 Provider를 요구함.\nprovider|%s", this.provider);
    }
}
