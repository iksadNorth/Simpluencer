package com.iksad.simpluencer.config;

import com.iksad.simpluencer.Properties.ServerProperties;
import lombok.Builder;

import java.util.HashMap;
import java.util.Map;

public class TemplateUtils {

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

        templateTable.put("registrationId", args.registrationId().toLowerCase());

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
