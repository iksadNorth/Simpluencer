package com.iksad.simpluencer.exception.ParserExceptionFactoryImpl;

import com.iksad.simpluencer.exception.ParserExceptionFactory;
import com.iksad.simpluencer.exception.SimpluencerException;

import java.util.List;
import java.util.Optional;

public class ParserSelector {
    private final List<? extends ParserExceptionFactory> parsers;

    public ParserSelector(Exception e) {
        this.parsers = List.of(
                new NotNullViolationParser(e),
                new UniqueViolationParser(e)
        );
    }

    public Optional<SimpluencerException> select() {
        for(ParserExceptionFactory parser : this.parsers) {
            if(parser.instanceOf())
                return Optional.of(parser);
        } return Optional.empty();
    }
}
