package com.iksad.simpluencer.exception.ErrorType;

import com.iksad.simpluencer.exception.SimpluencerException;
import org.springframework.http.HttpStatus;

public class ImageIOErrorType extends SimpluencerException {
    @Override
    public HttpStatus getHttpStatus() {
        return HttpStatus.INTERNAL_SERVER_ERROR;
    }

    @Override
    public String getMessageForClient() {
        return "이미지 저장 중 오류가 발생했습니다.";
    }

    @Override
    public String getMessageForServer() {
        return "이미지 발생 오류";
    }
}
