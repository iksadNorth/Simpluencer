package com.iksad.simpluencer.utils;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockMultipartFile;

import static com.iksad.simpluencer.tools.ImageTools.loadImage;
import static org.assertj.core.api.Assertions.assertThat;

@Slf4j
@DisplayName("[PhotoHandlerUtils]")
class PhotoHandlerUtilsTest {
    @Test @DisplayName("[setNameOfImage][정상]origin file name이 존재할 경우.")
    void setNameOfImage() {
        // given
        byte[] image = loadImage("https://cdn.pixabay.com/photo/2023/05/11/13/11/man-7986401_1280.jpg");
        String originalName = "mock original name";
        MockMultipartFile mockMultipartFile = new MockMultipartFile("mock name", originalName, null, image);

        // when
        String path = PhotoHandlerUtils.setNameOfImage(mockMultipartFile);

        //then
        log.info(path);
        assertThat(path).isEqualTo("mock_original_name");
    }

    @Test @DisplayName("[setNameOfImage][정상]origin file name이 존재하지 않는 경우.")
    void setNameOfImage_withNoFileName() {
        // given
        byte[] image = loadImage("https://cdn.pixabay.com/photo/2023/05/11/13/11/man-7986401_1280.jpg");
        String originalName = null;
        MockMultipartFile mockMultipartFile = new MockMultipartFile("mock name", originalName, null, image);

        // when
        String path = PhotoHandlerUtils.setNameOfImage(mockMultipartFile);

        //then
        log.info(path);
        assertThat(path).isNotBlank();
    }

    @Test @DisplayName("[makeRandomName][정상]")
    void makeRandomName() {
        // when
        String randomName = PhotoHandlerUtils.makeRandomName();

        // then
        log.info(randomName);
        assertThat(randomName).isNotBlank();
    }
}