package com.iksad.simpluencer.exception.Handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.iksad.simpluencer.exception.ErrorType.LoginFailType;
import com.iksad.simpluencer.model.response.ErrorResponse;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

import static com.iksad.simpluencer.utils.HttpParsingUtils.Arg;
import static com.iksad.simpluencer.utils.HttpParsingUtils.recordOnResponse;

@Slf4j
@Component
@RequiredArgsConstructor
public class LoginFailureHandler implements AuthenticationFailureHandler {
    private final ObjectMapper objectMapper;

    @Override
    public void onAuthenticationFailure(
            HttpServletRequest request,
            HttpServletResponse response,
            AuthenticationException exception
    ) throws IOException, ServletException {
        LoginFailType type = new LoginFailType();

        Arg args = Arg.builder()
                .request(request)
                .response(response)
                .objectMapper(objectMapper)
                .build();

        // server log
        String usernames = request.getParameter("username");
        String password = request.getParameter("password");

        log.info(
                type.getMessageForServer(usernames, password)
        );

        // client log
        response.setStatus(type.getHttpStatus().value());
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);

        ErrorResponse errorResponse = ErrorResponse.builder()
                .errorMessage(type.getMessageForClient())
                .build();

        recordOnResponse(args, errorResponse)
                .ifPresent(
                        e -> log.error(e.getMessage())
                );
    }
}
