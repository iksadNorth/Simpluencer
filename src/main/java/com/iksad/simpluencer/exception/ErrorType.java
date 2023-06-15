package com.iksad.simpluencer.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

import java.util.function.Function;

@RequiredArgsConstructor @Getter
public enum ErrorType {
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
}
