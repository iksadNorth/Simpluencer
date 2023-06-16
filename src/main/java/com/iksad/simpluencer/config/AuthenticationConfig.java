package com.iksad.simpluencer.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.header.writers.frameoptions.XFrameOptionsHeaderWriter;

import static org.springframework.security.web.util.matcher.AntPathRequestMatcher.antMatcher;

@Configuration
@RequiredArgsConstructor
public class AuthenticationConfig {
    private final AuthenticationFailureHandler authenticationFailureHandler;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .authorizeHttpRequests(
                        b -> b
                                .requestMatchers(antMatcher("/h2/**")).permitAll()
                                .requestMatchers(antMatcher("/css/**"), antMatcher("/js/**")).permitAll()
                                .anyRequest().permitAll()
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

                .anonymous(AbstractHttpConfigurer::disable)

                .csrf(
                        b -> b
                                .ignoringRequestMatchers(antMatcher("/h2/**"))
                )

                .build();
    }
}
