package com.iksad.simpluencer.exception.ErrorType;

import com.iksad.simpluencer.exception.SimpluencerException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@RequiredArgsConstructor
public class EmailNotFoundType extends SimpluencerException {
    private final String email;

    @Override
    public HttpStatus getHttpStatus() {
        return HttpStatus.NOT_FOUND;
    }

    @Override
    public String getMessageForClient() {
        return "해당 이메일은 존재하지 않습니다.";
    }

    public String getMessageForServer() {
        return String.format("존재하지 않는 이메일로 접근.\nemail|%s", this.email);
    }
}
