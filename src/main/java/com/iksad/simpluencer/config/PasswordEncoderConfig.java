package com.iksad.simpluencer.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class PasswordEncoderConfig {
    @Bean @Profile({"prod"})
    public PasswordEncoder passwordEncoderInProd() {
        return new BCryptPasswordEncoder();
    }

    @Bean @Profile({"local"})
    public PasswordEncoder passwordEncoderInLocal() {
        return new IdentityPasswordEncoder();
    }

    public static class IdentityPasswordEncoder implements PasswordEncoder {

        @Override
        public String encode(CharSequence rawPassword) {
            return rawPassword.toString();
        }

        @Override
        public boolean matches(CharSequence rawPassword, String encodedPassword) {
            return encodedPassword.equals(rawPassword.toString());
        }
    }
}
