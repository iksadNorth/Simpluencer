package com.iksad.simpluencer.exception.ParserExceptionFactoryImpl;

import com.iksad.simpluencer.exception.ParserExceptionFactory;
import com.iksad.simpluencer.model.ParsedExceptionResult;

public class UniqueViolationParser extends ParserExceptionFactory {
    public UniqueViolationParser(Exception e) {
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
