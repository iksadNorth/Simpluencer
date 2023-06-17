package com.iksad.simpluencer.exception.Handler;

import com.iksad.simpluencer.exception.DataIntegrityViolationExceptionParser.DataIntegrityViolationExceptionParser;
import com.iksad.simpluencer.exception.ErrorType.AbnormalDataIntegrityViolationType;
import com.iksad.simpluencer.exception.ExceptionParserFactory.ExceptionParserFactory;
import com.iksad.simpluencer.exception.SimpluencerException;
import com.iksad.simpluencer.model.response.ErrorResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
@RequiredArgsConstructor
public class ExceptionHandleController {
    private final ExceptionParserFactory exceptionParserFactory;

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
        SimpluencerException type = exceptionParserFactory.newInstance(e)
                .map(DataIntegrityViolationExceptionParser::newExceptionInstance)
                .map(SimpluencerException.class::cast)
                .orElse(new AbnormalDataIntegrityViolationType());
        return handleException(type);
    }

    @ExceptionHandler(SimpluencerException.class)
    public ResponseEntity<ErrorResponse> handleSimpluencerExceptionImpl(SimpluencerException e) {
        return handleException(e);
    }
}
