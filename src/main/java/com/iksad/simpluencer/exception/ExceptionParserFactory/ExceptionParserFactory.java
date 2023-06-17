package com.iksad.simpluencer.exception.ExceptionParserFactory;

import com.iksad.simpluencer.exception.DataIntegrityViolationExceptionParser.DataIntegrityViolationExceptionParser;

import java.util.Optional;

public interface ExceptionParserFactory {

    Optional<DataIntegrityViolationExceptionParser> newInstance(Exception e);
}
