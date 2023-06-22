package com.iksad.simpluencer.utils;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("[VarInspectUtils]")
class VarInspectUtilsTest {

    @DisplayName("[isBlank][정상]")
    @ParameterizedTest @NullAndEmptySource
    void isBlank(String str) {
        // when
        boolean result = VarInspectUtils.isBlank(str);

        // then
        assertThat(result).isTrue();
    }

    @DisplayName("[isBlank][비정상]")
    @ParameterizedTest @ValueSource(strings = {"mock", "1mock", "mock1", "mock mock", " mock", "mock "})
    void isBlank_fail(String str) {
        // when
        boolean result = VarInspectUtils.isBlank(str);

        // then
        assertThat(result).isFalse();
    }
}