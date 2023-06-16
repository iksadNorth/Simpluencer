package com.iksad.simpluencer.exception.ParserExceptionFactoryImpl;

import com.iksad.simpluencer.exception.ParserExceptionFactory;
import com.iksad.simpluencer.model.ParsedExceptionResult;

public class NotNullViolationParser extends ParserExceptionFactory {

    public NotNullViolationParser(Exception e) {
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
