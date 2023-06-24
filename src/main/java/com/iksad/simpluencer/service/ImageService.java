package com.iksad.simpluencer.service;

import org.springframework.web.multipart.MultipartFile;

public interface ImageService {
    byte[] loadImageAsBinaryData(String fileName);

    String save(MultipartFile file);
}
