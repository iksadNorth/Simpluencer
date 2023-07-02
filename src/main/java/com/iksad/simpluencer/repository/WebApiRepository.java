package com.iksad.simpluencer.repository;

import com.iksad.simpluencer.service.OAuth2TokenManager;
import lombok.RequiredArgsConstructor;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Map;
import java.util.function.Function;

@RequiredArgsConstructor
public abstract class WebApiRepository<Request, Response> {
    protected final RestTemplate restTemplate;
    private final OAuth2TokenManager oAuth2TokenManager;
    private final ParameterizedTypeReference<Map<String, Object>> parameterizedTypeReference = new ParameterizedTypeReference<>() {};

    public abstract HttpMethod getMethod();
    public abstract String getUri();
    public abstract void addHeaders(HttpHeaders headers);
    public abstract Object fromRequest(Request request);
    public abstract Response toResponse(Map<String, Object> response);

    public Response fetch(String providerName, String principalName, Request request) {
        if (getMethod().equals(HttpMethod.GET)) {
            return get(providerName, principalName, request);
        } else if (getMethod().equals(HttpMethod.POST)) {
            return post(providerName, principalName, request);
        } else {
            throw new IllegalArgumentException();
        }
    }


    private Response get(String providerName, String principalName, Request request) {
        HttpHeaders headers = getHeaders(providerName, principalName);
        String uriAddedParams = addParams((Map<String, Object>) fromRequest(request)).apply(getUri());

        HttpEntity<Void> httpEntity = new HttpEntity<>(null, headers);
        Map<String, Object> response = this.restTemplate.exchange(uriAddedParams, HttpMethod.GET, httpEntity, this.parameterizedTypeReference).getBody();
        return toResponse(response);
    }

    private Response post(String providerName, String principalName, Request body) {
        HttpHeaders headers = getHeaders(providerName, principalName);

        HttpEntity<Object> httpEntity = new HttpEntity<>(fromRequest(body), headers);
        Map<String, Object> response = this.restTemplate.exchange(getUri(), HttpMethod.GET, httpEntity, this.parameterizedTypeReference).getBody();
        return toResponse(response);
    }

    protected static HttpHeaders getHeaders(String accessToken) {
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(accessToken);
        return headers;
    }

    protected HttpHeaders getHeaders(String providerName, String principalName) {
        String accessToken = this.oAuth2TokenManager.getAccessToken(providerName, principalName);
        HttpHeaders headers = getHeaders(accessToken);
        this.addHeaders(headers);
        return headers;
    }

    protected static Function<String,String> addParams(Map<String, Object> params) {
        return uri -> UriComponentsBuilder.fromUriString(uri).uriVariables(params).toUriString();
    }
}
