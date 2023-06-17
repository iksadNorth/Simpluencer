package com.iksad.simpluencer.exception.DataIntegrityViolationExceptionParser;

import com.iksad.simpluencer.exception.ErrorType.DataIntegrityViolationType.ParsedExceptionResult;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.dao.DataIntegrityViolationException;

import static org.assertj.core.api.Assertions.assertThat;

class NotNullViolationParserTest {
    private static final DataIntegrityViolationException targetException = new DataIntegrityViolationException("""
                not-null property references a null or transient value : com.iksad.simpluencer.entity.Agent.email
                """.trim());
    private static final DataIntegrityViolationException otherException = new DataIntegrityViolationException("""
                other exception.
                """.trim());
    private NotNullViolationParser parser;

    @Test @DisplayName("[instanceOf][정상] 타겟 메시지인 경우.")
    void instanceOf() {
        // given - 의도된 에러가 발생할 때,
        DataIntegrityViolationException target = targetException;

        // when - NotNullViolationParser로 파싱을 하면,
        this.parser = new NotNullViolationParser(target);

        // then - True 출력.
        boolean instanceOf = this.parser.instanceOf();
        assertThat(instanceOf).isTrue();
    }

    @Test @DisplayName("[parse][정상] 타겟 메시지인 경우.")
    void parse() {
        // given - 의도된 에러가 발생할 때,
        DataIntegrityViolationException target = targetException;

        // when - NotNullViolationParser로 파싱을 하면,
        this.parser = new NotNullViolationParser(target);

        // then - 파싱 결과 출력.
        ParsedExceptionResult result = this.parser.parse();
        assertThat(result.reason()).isEqualTo("not-null property references a null or transient value");
        assertThat(result.column()).isEqualTo("email");
        assertThat(result.input()).isNull();
    }

    @Test @DisplayName("[instanceOf][정상] 타겟 메시지가 아닌 경우.")
    void instanceOf_Other_Exception() {
        // given - 의도되지 않은 에러가 발생할 때,
        DataIntegrityViolationException target = otherException;

        // when - NotNullViolationParser로 파싱을 하면,
        this.parser = new NotNullViolationParser(target);

        // then - False 출력.
        boolean instanceOf = this.parser.instanceOf();
        assertThat(instanceOf).isFalse();
    }
}