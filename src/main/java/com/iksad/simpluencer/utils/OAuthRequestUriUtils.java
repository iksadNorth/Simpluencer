package com.iksad.simpluencer.utils;

import org.springframework.data.util.Pair;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.function.BiConsumer;
import java.util.function.Function;
import java.util.stream.Stream;

public class OAuthRequestUriUtils {
    private final static MultiValueMap<String, String> defaultQueries = new LinkedMultiValueMap<>();
    private final static List<Pair<String, Function<ClientRegistration,String>>> additionQueryFunc = new LinkedList<>();

    private final static String QUERY_DELIMITER = "&";
    private final static String KEY_VALUE_DELIMITER = "=";
    private final static String SCOPE_DELIMITER = " ";

    static {
        defaultQueries.add("response_type", "code");
        defaultQueries.add("access_type", "offline");
        defaultQueries.add("prompt", "consent");

        additionQueryFunc.add(Pair.of("redirect_uri", ClientRegistration::getRedirectUri));
        additionQueryFunc.add(Pair.of("client_id", ClientRegistration::getClientId));
        additionQueryFunc.add(Pair.of("scope", clientRegistration -> String.join(SCOPE_DELIMITER, clientRegistration.getScopes())));
    }

    private static void parseQuery(String rawQueries, BiConsumer<String, String> mapperKeyAndValue) {
        Optional.ofNullable(rawQueries)
                .map(query -> query.split(QUERY_DELIMITER)).stream()
                .flatMap(Stream::of)
                .map(entry -> entry.split(KEY_VALUE_DELIMITER))
                .forEach(keyAndValue -> {
                    mapperKeyAndValue.accept(keyAndValue[0], keyAndValue[1]);
                });
    }

    public static String getUri(ClientRegistration clientRegistration) {
        UriComponentsBuilder uriComponentsBuilder = UriComponentsBuilder.fromUriString(
                        clientRegistration.getProviderDetails().getAuthorizationUri()
                );

        MultiValueMap<String, String> existedQueries = new LinkedMultiValueMap<>();
        parseQuery(uriComponentsBuilder.build().getQuery(), existedQueries::add);

        MultiValueMap<String, String> additionQueries = new LinkedMultiValueMap<>();
        additionQueryFunc.stream()
                .map(pair -> Pair.of(pair.getFirst(), pair.getSecond().apply(clientRegistration)))
                .forEach(pair -> additionQueries.add(pair.getFirst(), pair.getSecond()));

        MultiValueMap<String, String> queries = new LinkedMultiValueMap<>();
        queries.addAll(defaultQueries);
        queries.addAll(existedQueries);
        queries.addAll(additionQueries);

        return uriComponentsBuilder.replaceQueryParams(queries)
                .build().toUriString();
    }
}
