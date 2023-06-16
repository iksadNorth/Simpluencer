package com.iksad.simpluencer.utils;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

public class ResponseEntityUtils {

    public static ResponseEntity<Void> getRedirectionResponse(String url) {
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.LOCATION, url);
        headers.add(HttpHeaders.CONTENT_TYPE, MediaType.TEXT_HTML_VALUE);
        return new ResponseEntity<>(headers, HttpStatus.FOUND);
    }

    public static ResponseEntity<Void> getOkResponse() {
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
