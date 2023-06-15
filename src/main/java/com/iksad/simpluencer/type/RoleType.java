package com.iksad.simpluencer.type;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.stream.Stream;

@RequiredArgsConstructor @Getter
public enum RoleType {
    USER("ROLE_USER"),
    ADMIN("ROLE_ADMIN");

    private final String dbName;

    @Converter(autoApply = true)
    public static class ConverterImpl implements AttributeConverter<RoleType, String> {

        @Override
        public String convertToDatabaseColumn(RoleType attribute) {
            return attribute.getDbName();
        }

        @Override
        public RoleType convertToEntityAttribute(String dbData) {
            return Stream.of(RoleType.values())
                    .filter(r -> convertToDatabaseColumn(r).equals(dbData))
                    .findFirst()
                    .orElseThrow(IllegalArgumentException::new);
        }
    }
}
