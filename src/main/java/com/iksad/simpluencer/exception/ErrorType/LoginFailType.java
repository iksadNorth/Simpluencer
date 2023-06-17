package com.iksad.simpluencer.exception.ErrorType;

import com.iksad.simpluencer.exception.SimpluencerException;
import org.springframework.http.HttpStatus;

public class LoginFailType extends SimpluencerException {
    @Override
    public HttpStatus getHttpStatus() {
        return HttpStatus.UNAUTHORIZED;
    }

    @Override
    public String getMessageForClient() {
        return "로그인 실패!!";
    }

    @Override
    public String getMessageForServer() {
        return "로그인 실패";
    }

    public String getMessageForServer(String email, String password) {
        return String.format("로그인 실패\nemail|%s\npassword|%s", email, password);
    }
}
