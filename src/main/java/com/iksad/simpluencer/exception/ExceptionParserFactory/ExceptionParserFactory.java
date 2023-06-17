package com.iksad.simpluencer.exception.ExceptionParserFactory;

import com.iksad.simpluencer.exception.DataIntegrityViolationExceptionParser.DataIntegrityViolationExceptionParser;
import org.springframework.dao.DataIntegrityViolationException;

import java.util.Optional;

public interface ExceptionParserFactory {

    Optional<DataIntegrityViolationExceptionParser> newInstance(DataIntegrityViolationException e);
}
