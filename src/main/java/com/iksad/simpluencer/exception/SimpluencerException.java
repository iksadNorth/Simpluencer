package com.iksad.simpluencer.exception;

import org.springframework.http.HttpStatus;

public abstract class SimpluencerException extends RuntimeException {
    public abstract HttpStatus getHttpStatus();
    public abstract String getMessageForClient();
    public abstract String getMessageForServer();
}
