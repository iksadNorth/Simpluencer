package com.iksad.simpluencer.exception.ExceptionParserFactory;

import com.iksad.simpluencer.exception.DataIntegrityViolationExceptionParser.DataIntegrityViolationExceptionParser;
import com.iksad.simpluencer.exception.DataIntegrityViolationExceptionParser.NotNullViolationParser;
import com.iksad.simpluencer.exception.DataIntegrityViolationExceptionParser.UniqueViolationParser;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class ParserSelectorFactory implements ExceptionParserFactory {
    private static final List<Class<? extends DataIntegrityViolationExceptionParser>> parserClasses = List.of(
        NotNullViolationParser.class,
        UniqueViolationParser.class
    );

    public Optional<DataIntegrityViolationExceptionParser> newInstance(DataIntegrityViolationException e) {
        for(Class<? extends DataIntegrityViolationExceptionParser> parserClass : parserClasses) {
            DataIntegrityViolationExceptionParser parser;
            try {
                parser = parserClass
                        .getConstructor(DataIntegrityViolationException.class)
                        .newInstance(e);
            } catch (ReflectiveOperationException ex) {
                continue;
            }  if(parser.instanceOf()) {
                return Optional.of(parser);
            }
        } return Optional.empty();
    }
}
