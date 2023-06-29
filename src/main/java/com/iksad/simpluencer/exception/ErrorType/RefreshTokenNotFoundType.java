package com.iksad.simpluencer.exception.ErrorType;

import com.iksad.simpluencer.exception.SimpluencerException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@RequiredArgsConstructor
public class RefreshTokenNotFoundType extends SimpluencerException {

    @Override
    public HttpStatus getHttpStatus() {
        return HttpStatus.NOT_FOUND;
    }

    @Override
    public String getMessageForClient() {
        return "인증 토큰이 소실되었습니다. '프로필 설정' 페이지에서 플랫폼을 삭제하고 갱신해주세요.";
    }

    public String getMessageForServer() {
        return "Refresh Token이 원인모를 이유로 유실됨.";
    }
}
