package com.iksad.simpluencer.exception;

import org.springframework.http.HttpStatus;

public interface SimpluencerException {
    HttpStatus getHttpStatus();
    String getMessageForClient();
    String getMessageForServer();
}
