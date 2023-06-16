package com.iksad.simpluencer.utils;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class ResponseEntityUtils {

    public static ResponseEntity<Void> getRedirectionResponse(String url) {
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.LOCATION, url);
        return new ResponseEntity<>(headers, HttpStatus.FOUND);
    }
}
