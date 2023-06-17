package com.iksad.simpluencer.exception.DataIntegrityViolationExceptionParser;

import com.iksad.simpluencer.exception.ErrorType.DataIntegrityViolationType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.dao.DataIntegrityViolationException;

import static org.assertj.core.api.Assertions.assertThat;

class UniqueViolationParserTest {
    private static final DataIntegrityViolationException targetException = new DataIntegrityViolationException("""
                could not execute statement [Unique index or primary key violation: "PUBLIC.CONSTRAINT_INDEX_3 ON PUBLIC.AGENT(EMAIL NULLS FIRST) VALUES ( /* 2 */ 'mock email' )"; SQL statement:
                insert into agent (created_at,email,nickname,password,id) values (?,?,?,?,default) [23505-214]] [insert into agent (created_at,email,nickname,password,id) values (?,?,?,?,default)]; SQL [insert into agent (created_at,email,nickname,password,id) values (?,?,?,?,default)]; constraint ["PUBLIC.CONSTRAINT_INDEX_3 ON PUBLIC.AGENT(EMAIL NULLS FIRST) VALUES ( /* 2 */ 'mock email' )"; SQL statement:
                insert into agent (created_at,email,nickname,password,id) values (?,?,?,?,default) [23505-214]]
                """.trim());
    private static final DataIntegrityViolationException otherException = new DataIntegrityViolationException("""
                other exception.
                """.trim());
    private UniqueViolationParser parser;

    @Test @DisplayName("[instanceOf][정상] 타겟 메시지인 경우.")
    void instanceOf() {
        // given - 의도된 에러가 발생할 때,
        DataIntegrityViolationException target = targetException;

        // when - UniqueViolationParser로 파싱을 하면,
        this.parser = new UniqueViolationParser(target);

        // then - True 출력.
        boolean instanceOf = this.parser.instanceOf();
        assertThat(instanceOf).isTrue();
    }

    @Test @DisplayName("[parse][정상] 타겟 메시지인 경우.")
    void parse() {
        // given - 의도된 에러가 발생할 때,
        DataIntegrityViolationException target = targetException;

        // when - UniqueViolationParser로 파싱을 하면,
        this.parser = new UniqueViolationParser(target);

        // then - 파싱 결과 출력.
        DataIntegrityViolationType.ParsedExceptionResult result = this.parser.parse();
        assertThat(result.reason()).isEqualTo("Unique index or primary key violation");
        assertThat(result.column()).isEqualTo("email");
        assertThat(result.input()).isEqualTo("mock email");
    }

    @Test @DisplayName("[instanceOf][정상] 타겟 메시지가 아닌 경우.")
    void instanceOf_Other_Exception() {
        // given - 의도되지 않은 에러가 발생할 때,
        DataIntegrityViolationException target = otherException;

        // when - UniqueViolationParser로 파싱을 하면,
        this.parser = new UniqueViolationParser(target);

        // then - False 출력.
        boolean instanceOf = this.parser.instanceOf();
        assertThat(instanceOf).isFalse();
    }
}