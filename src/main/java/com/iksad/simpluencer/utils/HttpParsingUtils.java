package com.iksad.simpluencer.utils;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.Builder;

import java.io.IOException;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

public class HttpParsingUtils {

    @Builder(toBuilder = true)
    public record Arg(
            ObjectMapper objectMapper,
            HttpServletRequest request,
            HttpServletResponse response
    ) {}

    public static String getBodyInRequest(Arg dto) throws IOException {
        return dto.request.getReader()
                .lines()
                .collect(
                        Collectors.joining(System.lineSeparator())
                );
    }

    public static Map<String, String[]> parseFormData(Arg dto) throws IOException {
        String requestBody = getBodyInRequest(dto);

        TypeReference<Map<String, String[]>> typeRef = new TypeReference<>() {};
        return dto.objectMapper.readValue(requestBody, typeRef);
    }

    public static <T> Optional<IOException> recordOnResponse(Arg dto, T response) {
        IOException error = null;

        try (ServletOutputStream os = dto.response.getOutputStream()) {
            byte[] bytes = dto.objectMapper.writeValueAsBytes(response);

            os.write(bytes);
            os.flush();

        } catch (IOException e) {
            error = e;
        }

        return Optional.ofNullable(error);
    }
}
