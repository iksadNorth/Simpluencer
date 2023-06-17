package com.iksad.simpluencer.config;

import com.iksad.simpluencer.config.EmailContent.PasswordResetEmailContent;
import com.iksad.simpluencer.entity.Agent;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class EmailContentConfig {
    @Bean
    public PasswordResetEmailContent passwordResetEmailContent() {
        return new PasswordResetEmailContent() {
            @Override
            public String getTitle(Agent agent) {
                return """
                [Simpluencer] 비밀 번호 재설정 메일
                """.trim();
            }
            @Override
            public String getContent(Agent agent, String newPassword) {
                return String.format("""
                보안을 위해 로그인 후, 비밀 번호를 변경해주세요.
                대소문자를 구분해서 입력해주세요.
                새롭게 변경된 비밀 번호는 다음과 같습니다.
                
                변경된 비밀 번호:
                %s
                
                """, newPassword).trim();
            }
        };
    }
}
