package com.iksad.simpluencer.exception.DataIntegrityViolationExceptionParser;

import com.iksad.simpluencer.exception.ErrorType.DataIntegrityViolationType;
import com.iksad.simpluencer.exception.ErrorType.DataIntegrityViolationType.ParsedExceptionResult;
import org.springframework.dao.DataIntegrityViolationException;

public class NotNullViolationParser extends DataIntegrityViolationExceptionParser {
    public NotNullViolationParser(DataIntegrityViolationException e) {
        super(e);
    }

    @Override
    public boolean instanceOf() {
        return e.getMessage()
                .startsWith("not-null property references a null or transient value");
    }

    @Override
    public ParsedExceptionResult parse() {
        String[] pieces = e.getMessage().split("[:.]");
        String reason = pieces[0].trim();
        String column = pieces[pieces.length - 1];
        return ParsedExceptionResult.builder()
                .reason(reason)
                .column(column)
                .build();
    }
}
