package com.iksad.simpluencer.exception.ErrorType;

import com.iksad.simpluencer.exception.SimpluencerException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@RequiredArgsConstructor
public class NoticeNotFoundType extends SimpluencerException {
    private final Long id;

    @Override
    public HttpStatus getHttpStatus() {
        return HttpStatus.NOT_FOUND;
    }

    @Override
    public String getMessageForClient() {
        return "해당 공지는 존재하지 않습니다.";
    }

    public String getMessageForServer() {
        return String.format("존재하지 않는 Notice로 접근.\nid|%s", this.id);
    }
}
