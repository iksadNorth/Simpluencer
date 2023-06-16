package com.iksad.simpluencer.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

import java.util.function.Function;

@RequiredArgsConstructor @Getter
public enum ErrorType implements SimpluencerException {
    DATA_INTEGRITY_VIOLATION(
            HttpStatus.BAD_REQUEST,
            "비정상적인 입력 값이 들어왔습니다.",
            args -> String.format("원인 모를 버그 발생.", args)
    ),
    LOGIN_FAIL(
            HttpStatus.UNAUTHORIZED,
            "로그인 실패!!",
            args -> String.format("로그인 실패\nemail|%s\npassword|%s", args)
    );

    private final HttpStatus httpStatus;
    private final String messageForClient;
    private final Function<Object[], String> messageForServer;

    public String getMessageForServer(Object... args) {
        return this.messageForServer.apply(args);
    }
    public String getMessageForServer() {
        return this.messageForServer.apply(null);
    }

    public SimpluencerExceptionImpl toException(Object... args) {
        return new SimpluencerExceptionImpl(
                this.getHttpStatus(), this.getMessageForServer(), this.getMessageForServer(args)
        );
    }
}
