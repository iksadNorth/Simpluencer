package com.iksad.simpluencer.config;

import com.iksad.simpluencer.service.OAuth2PanelService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientService;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.header.writers.frameoptions.XFrameOptionsHeaderWriter;

import static org.springframework.security.web.util.matcher.AntPathRequestMatcher.antMatcher;

@Configuration
@RequiredArgsConstructor
public class AuthenticationConfig {
    private final AuthenticationFailureHandler authenticationFailureHandler;
    private final OAuth2PanelService oAuth2PanelService;
    private final ClientRegistrationRepository clientRegistrationRepository;
    private final OAuth2AuthorizedClientService oAuth2AuthorizedClientService;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .authorizeHttpRequests(
                        b -> b
                                .requestMatchers(antMatcher("/h2/**")).permitAll()
                                .requestMatchers(antMatcher("/css/**"), antMatcher("/js/**")).permitAll()

                                .requestMatchers(antMatcher(HttpMethod.GET, "/platform/create")).authenticated()

                                .requestMatchers(antMatcher(HttpMethod.PATCH, "/api/v1/panel/**")).authenticated()
                                .requestMatchers(antMatcher(HttpMethod.DELETE, "/api/v1/panel/**")).authenticated()

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

                .oauth2Login(
                        b -> b
                                .defaultSuccessUrl("/platform/create")

                                .clientRegistrationRepository(clientRegistrationRepository)
                                .authorizedClientService(oAuth2AuthorizedClientService)

                                .userInfoEndpoint(
                                        e -> e
                                                .userService(oAuth2PanelService)
                                )
                )

                .build();
    }
}
