package com.iksad.simpluencer.exception.ErrorType;

import com.iksad.simpluencer.exception.SimpluencerException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@RequiredArgsConstructor
public class PanelNotFoundType extends SimpluencerException {
    private final Long id;

    @Override
    public HttpStatus getHttpStatus() {
        return HttpStatus.NOT_FOUND;
    }

    @Override
    public String getMessageForClient() {
        return "해당 플랫폼의 계정은 존재하지 않습니다.";
    }

    public String getMessageForServer() {
        return String.format("존재하지 않는 Panel로 접근.\nid|%s", this.id);
    }
}
