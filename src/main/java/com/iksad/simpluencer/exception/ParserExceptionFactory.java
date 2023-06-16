package com.iksad.simpluencer.exception;

import com.iksad.simpluencer.model.ParsedExceptionResult;
import org.springframework.http.HttpStatus;

import java.util.Optional;

public abstract class ParserExceptionFactory implements SimpluencerException {
    protected final Exception e;
    protected final ParsedExceptionResult result;

    public ParserExceptionFactory(Exception e) {
        this.e = e;
        this.result = this.instanceOf() ? this.parse() : null;
    }

    @Override
    public HttpStatus getHttpStatus() {
        return HttpStatus.BAD_REQUEST;
    }

    @Override
    public String getMessageForClient() {
        if(this.result == null) { return ""; }
        return String.format("'%s'가 %s 값으로 입력됐습니다.",  this.result.column(), this.getInput());
    }

    @Override
    public String getMessageForServer() {
        if(this.result == null) { return ""; }
        return String.format("DB 칼럼 제약 조건 훼손.\ncolumn|%s\ninput|%s", this.result.column(), this.result.input());
    }

    protected String getInput() {
        return Optional.ofNullable(this.result.input())
                .map(s -> String.format("'%s'", s))
                .orElse("빈");
    }

    public abstract boolean instanceOf();
    public abstract ParsedExceptionResult parse();
}
