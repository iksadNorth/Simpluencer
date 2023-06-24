package com.iksad.simpluencer.service;

import com.iksad.simpluencer.Properties.ImageBucketProperties;
import com.iksad.simpluencer.exception.ErrorType.ImageIOErrorType;
import com.iksad.simpluencer.utils.PhotoHandlerUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

@Service
@RequiredArgsConstructor
public class ImageServiceImpl implements ImageService {
    private final ImageBucketProperties imageBucketProperties;

    @Override
    public byte[] loadImageAsBinaryData(String fileName) {
        Path path = Path.of(imageBucketProperties.getPath(), fileName);
        try {
            return Files.readAllBytes(path);
        } catch (IOException e) {
            throw new ImageIOErrorType();
        }
    }

    public String save(MultipartFile file) {
        String originalFilename = PhotoHandlerUtils.setNameOfImage(file);
        try {
            file.transferTo(Path.of(imageBucketProperties.getPath(), originalFilename));
        } catch (IOException e) {
            throw new ImageIOErrorType();
        }
        return originalFilename;
    }
}
