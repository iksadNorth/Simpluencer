package com.iksad.simpluencer.exception.DataIntegrityViolationExceptionParser;

import com.iksad.simpluencer.exception.ErrorType.DataIntegrityViolationType.ParsedExceptionResult;
import org.springframework.dao.DataIntegrityViolationException;

public class UniqueViolationParser extends DataIntegrityViolationExceptionParser {
    public UniqueViolationParser(DataIntegrityViolationException e) {
        super(e);
    }

    @Override
    public boolean instanceOf() {
        return e.getMessage()
                .contains("Unique index or primary key violation");
    }

    @Override
    public ParsedExceptionResult parse() {
        String[] pieces = e.getMessage().split("[\\[:\\(\\)']");
        return ParsedExceptionResult.builder()
                .reason(pieces[1])
                .column(pieces[3].split("\s")[0].toLowerCase())
                .input(pieces[6])
                .build();
    }
}
