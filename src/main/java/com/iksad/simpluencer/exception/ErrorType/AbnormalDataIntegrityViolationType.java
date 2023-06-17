package com.iksad.simpluencer.exception.ErrorType;

import com.iksad.simpluencer.exception.SimpluencerException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@RequiredArgsConstructor
public class AbnormalDataIntegrityViolationType extends SimpluencerException {
    @Override
    public HttpStatus getHttpStatus() {
        return HttpStatus.BAD_REQUEST;
    }

    @Override
    public String getMessageForClient() {
        return "비정상적인 값이 입력되었습니다. 다른 값을 입력해주세요.";
    }

    @Override
    public String getMessageForServer() {
        return "원인 모를 오류가 발생되었습니다. ExceptionParser.instanceOf() 메서드로 판단할 수 없는 에러메시지인 것 같습니다.";
    }
}
