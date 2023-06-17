package com.iksad.simpluencer.exception.DataIntegrityViolationExceptionParser;

import com.iksad.simpluencer.exception.ErrorType.DataIntegrityViolationType;
import com.iksad.simpluencer.exception.ErrorType.DataIntegrityViolationType.ParsedExceptionResult;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;

@RequiredArgsConstructor
public abstract class DataIntegrityViolationExceptionParser {
    protected final DataIntegrityViolationException e;

    public DataIntegrityViolationType newExceptionInstance() {
        return new DataIntegrityViolationType(this.parse());
    }

    public abstract boolean instanceOf();
    public abstract ParsedExceptionResult parse();
}
