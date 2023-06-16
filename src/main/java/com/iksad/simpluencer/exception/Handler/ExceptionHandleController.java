package com.iksad.simpluencer.exception.Handler;

import com.iksad.simpluencer.exception.ErrorType;
import com.iksad.simpluencer.exception.ParserExceptionFactoryImpl.ParserSelector;
import com.iksad.simpluencer.exception.SimpluencerException;
import com.iksad.simpluencer.exception.SimpluencerExceptionImpl;
import com.iksad.simpluencer.model.response.ErrorResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class ExceptionHandleController {
    private ResponseEntity<ErrorResponse> handleException(SimpluencerException type) {
        // server log
        log.info(
                type.getMessageForServer()
        );

        // client log
        String messageForClient = type.getMessageForClient();
        ErrorResponse errorResponse = ErrorResponse.builder()
                .errorMessage(messageForClient)
                .build();

        return new ResponseEntity<>(errorResponse, type.getHttpStatus());
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<ErrorResponse> handleDataIntegrityViolationException(DataIntegrityViolationException e) {
        SimpluencerException type = new ParserSelector(e).select()
                .orElse(ErrorType.DATA_INTEGRITY_VIOLATION);
        return handleException(type);
    }

    @ExceptionHandler(SimpluencerExceptionImpl.class)
    public ResponseEntity<ErrorResponse> handleSimpluencerExceptionImpl(SimpluencerExceptionImpl e) {
        return handleException(e);
    }
}
