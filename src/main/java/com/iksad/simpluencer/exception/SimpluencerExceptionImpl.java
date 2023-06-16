package com.iksad.simpluencer.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@RequiredArgsConstructor @Getter
public class SimpluencerExceptionImpl extends RuntimeException implements SimpluencerException {
    private final HttpStatus httpStatus;
    private final String messageForClient;
    private final String messageForServer;
}
