package com.iksad.simpluencer.utils;

import com.iksad.simpluencer.Properties.ServerProperties;
import com.iksad.simpluencer.type.OAuth2ProviderType;
import lombok.Builder;

import java.util.HashMap;
import java.util.Map;

public class TemplateUtils {
    public static final String DEFAULT_REDIRECT_URL = "{baseUrl}/login/oauth2/code/{registrationId}";

    @Builder(toBuilder = true)
    public record Args(
            ServerProperties serverProperties,
            String registrationId
    ) {}

    private static Map<String, String> _tables(Args args) {
        HashMap<String, String> templateTable = new HashMap<>();

        ServerProperties serverProperties = args.serverProperties();
        String baseUrl = String.format("%s://%s:%s", serverProperties.getSchema(), serverProperties.getAddress(), serverProperties.getPort());
        templateTable.put("baseUrl", baseUrl);

        OAuth2ProviderType nameForRedirectUrl = OAuth2ProviderType.valueOf(args.registrationId());
        templateTable.put("registrationId", nameForRedirectUrl.getProviderNameForRedirectUrl());

        return templateTable;
    }

    private static String wrap(String key) {
        return String.format("\\{%s\\}", key);
    }

    public static String substituteProperties(String template, Args args) {
        for(Map.Entry<String, String> entry : _tables(args).entrySet()) {
            template = template.replaceAll(wrap(entry.getKey()), entry.getValue());
        } return template;
    }
}
