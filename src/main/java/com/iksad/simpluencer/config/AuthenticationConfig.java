package com.iksad.simpluencer.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.CsrfConfigurer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.header.writers.frameoptions.XFrameOptionsHeaderWriter;

@Configuration
@RequiredArgsConstructor
public class AuthenticationConfig {
    private final AuthenticationFailureHandler authenticationFailureHandler;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .authorizeHttpRequests(
                        b -> b.anyRequest().permitAll()
                )

                .headers(
                        b -> b
                                .addHeaderWriter(new XFrameOptionsHeaderWriter(
                                        XFrameOptionsHeaderWriter.XFrameOptionsMode.SAMEORIGIN
                                ))
                )

                .httpBasic(AbstractHttpConfigurer::disable)

                .formLogin(
                        b -> b
                                .loginPage("/auth/login")

                                .loginProcessingUrl("/api/v1/auth/login")
                                .usernameParameter("username")
                                .passwordParameter("password")

                                .defaultSuccessUrl("/")
                                .failureHandler(authenticationFailureHandler)

                                .permitAll()
                )

                .logout(
                        b -> b
                                .logoutUrl("/api/v1/auth/logout")

                                .logoutSuccessUrl("/auth/login")
                )

                .csrf(CsrfConfigurer::disable)

                .anonymous(AbstractHttpConfigurer::disable)

                .build();
    }
}
