package com.iksad.simpluencer.model.request;

import com.iksad.simpluencer.entity.Panel;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

@Slf4j
@DisplayName("[PanelUpdateRequest]")
class PanelUpdateRequestTest {

    @Test @DisplayName("[overwrite][정상]정상 요청이 들어왔을 때, 정상적으로 덮어씌우기")
    void overwrite() {
        // Given
        Panel entity = new Panel();
        entity.setProvider("constant provider");
        entity.setDescription("before description");
        entity.setLocation(1);

        PanelUpdateRequest request = PanelUpdateRequest.builder()
                .description("after description")
                .location(10)
                .build();

        // When
        request.overwrite(entity);

        // then
        assertThat(entity.getProvider()).isEqualTo("constant provider");
        assertThat(entity.getDescription()).isEqualTo("after description");
        assertThat(entity.getLocation()).isEqualTo(10);
    }

    @Test @DisplayName("[overwrite][정상]null 값의 요청이 들어왔을 때, 정상적으로 덮어씌우기")
    void overwriteWhen_Null_Input() {
        // Given
        Panel entity = new Panel();
        entity.setProvider("constant provider");
        entity.setDescription("before description");
        entity.setLocation(1);

        PanelUpdateRequest request = PanelUpdateRequest.builder()
                .description(null)
                .location(10)
                .build();

        // When
        request.overwrite(entity);

        // then
        assertThat(entity.getProvider()).isEqualTo("constant provider");
        assertThat(entity.getDescription()).isEqualTo("before description");
        assertThat(entity.getLocation()).isEqualTo(10);
    }

    @Test @DisplayName("[overwrite][정상]공백 문자값의 요청이 들어왔을 때, 정상적으로 덮어씌우기")
    void overwriteWhen_Blank_Input() {
        // Given
        Panel entity = new Panel();
        entity.setProvider("constant provider");
        entity.setDescription("before description");
        entity.setLocation(1);

        PanelUpdateRequest request = PanelUpdateRequest.builder()
                .description("    ")
                .location(10)
                .build();

        // When
        request.overwrite(entity);

        // then
        assertThat(entity.getProvider()).isEqualTo("constant provider");
        assertThat(entity.getDescription()).isEqualTo("before description");
        assertThat(entity.getLocation()).isEqualTo(10);
    }
}