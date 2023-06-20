package com.iksad.simpluencer.exception.ErrorType;

import com.iksad.simpluencer.exception.SimpluencerException;
import org.springframework.http.HttpStatus;

public class ForbiddenUser extends SimpluencerException {
    @Override
    public HttpStatus getHttpStatus() {
        return HttpStatus.FORBIDDEN;
    }

    @Override
    public String getMessageForClient() {
        return "접근 권한이 없습니다.";
    }

    @Override
    public String getMessageForServer() {
        return "인가 오류 발생";
    }
}
