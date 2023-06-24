package com.iksad.simpluencer.utils;

import org.springframework.web.multipart.MultipartFile;

import java.util.Optional;

public class PhotoHandlerUtils {

    public static String setNameOfImage(MultipartFile file) {
        return Optional.ofNullable(file.getOriginalFilename())
                .filter(s -> !s.isBlank())
                .map(s -> s.replaceAll("\s", "_"))
                .orElseGet(PhotoHandlerUtils::makeRandomName);
    }

    public static String makeRandomName() {
        long now = System.currentTimeMillis();
        String stem = String.valueOf(now);
        return String.format("%s.png", stem);
    }
}
