package com.iksad.simpluencer.config;

import com.iksad.simpluencer.Properties.ServerProperties;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class TemplateUtilsTest {
    @Test
    void substituteProperties() {
        // Given
        ServerProperties serverProperties = new ServerProperties();
        serverProperties.setSchema("http");
        serverProperties.setAddress("localhost");
        serverProperties.setPort(8080);
        String template = "{baseUrl}/login/oauth2/code/{registrationId}";

        TemplateUtils.Args args = TemplateUtils.Args.builder()
                .registrationId("GOOGLE")
                .serverProperties(serverProperties)
                .build();

        // When
        String replaced = TemplateUtils.substituteProperties(template, args);

        // Then
        assertThat(replaced).isEqualTo("http://localhost:8080/login/oauth2/code/google");
    }
}