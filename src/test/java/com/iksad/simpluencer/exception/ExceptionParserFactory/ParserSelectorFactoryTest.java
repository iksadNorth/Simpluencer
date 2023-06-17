package com.iksad.simpluencer.exception.ExceptionParserFactory;

import com.iksad.simpluencer.exception.DataIntegrityViolationExceptionParser.DataIntegrityViolationExceptionParser;
import com.iksad.simpluencer.exception.DataIntegrityViolationExceptionParser.NotNullViolationParser;
import com.iksad.simpluencer.exception.DataIntegrityViolationExceptionParser.UniqueViolationParser;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.dao.DataIntegrityViolationException;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

class ParserSelectorFactoryTest {
    private ParserSelectorFactory parserSelectorFactory;

    private static final DataIntegrityViolationException nonUniqueException = new DataIntegrityViolationException("""
                could not execute statement [Unique index or primary key violation: "PUBLIC.CONSTRAINT_INDEX_3 ON PUBLIC.AGENT(EMAIL NULLS FIRST) VALUES ( /* 2 */ 'mock email' )"; SQL statement:
                insert into agent (created_at,email,nickname,password,id) values (?,?,?,?,default) [23505-214]] [insert into agent (created_at,email,nickname,password,id) values (?,?,?,?,default)]; SQL [insert into agent (created_at,email,nickname,password,id) values (?,?,?,?,default)]; constraint ["PUBLIC.CONSTRAINT_INDEX_3 ON PUBLIC.AGENT(EMAIL NULLS FIRST) VALUES ( /* 2 */ 'mock email' )"; SQL statement:
                insert into agent (created_at,email,nickname,password,id) values (?,?,?,?,default) [23505-214]]
                """.trim());
    private static final DataIntegrityViolationException nullException = new DataIntegrityViolationException("""
                not-null property references a null or transient value : com.iksad.simpluencer.entity.Agent.email
                """.trim());

    @Test @DisplayName("[newInstance][정상]")
    void newInstance() {
        // Given
        parserSelectorFactory = new ParserSelectorFactory();

        // When
        Optional<DataIntegrityViolationExceptionParser> nonUniqueExceptionParser = parserSelectorFactory.newInstance(nonUniqueException);
        Optional<DataIntegrityViolationExceptionParser> nullExceptionParser = parserSelectorFactory.newInstance(nullException);

        // then
        assertThat(nonUniqueExceptionParser.isPresent()).isTrue();
        assertThat(nullExceptionParser.isPresent()).isTrue();

        assert nonUniqueExceptionParser.get() instanceof UniqueViolationParser;
        assert nullExceptionParser.get() instanceof NotNullViolationParser;

    }
}