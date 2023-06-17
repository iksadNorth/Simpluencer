package com.iksad.simpluencer.exception.ErrorType;

import com.iksad.simpluencer.exception.SimpluencerException;
import lombok.Builder;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

import java.util.Optional;

@RequiredArgsConstructor
public class DataIntegrityViolationType extends SimpluencerException {
    @Builder(toBuilder = true)
    public record ParsedExceptionResult(
            String reason,
            String column,
            String input
    ) {}

    protected final ParsedExceptionResult result;

    @Override
    public HttpStatus getHttpStatus() {
        return HttpStatus.BAD_REQUEST;
    }

    @Override
    public String getMessageForClient() {
        return String.format(this.getComment(),  this.result.column(), this.getInput());
    }

    @Override
    public String getMessageForServer() {
        return String.format("DB 칼럼 제약 조건 훼손.\ncolumn|%s\ninput|%s", this.result.column(), this.result.input());
    }

    protected String getInput() {
        return Optional.ofNullable(this.result.input())
                .map(s -> String.format("'%s'", s))
                .orElse("");
    }

    protected String getComment() {
        if(this.result.input() == null) {
            return "'%s'에 입력된 값이 없습니다.%s";
        } else {
            return "'%s'에 입력된 값인 '%s'이 이미 존재합니다.";
        }
    }
}
