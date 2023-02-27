package com.snowboard_rental_crm.shared_module.service;


import com.snowboard_rental_crm.shared_data.result.ResponseResult;
import com.snowboard_rental_crm.shared_module.exeption.ExceptionConverter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.reactive.ClientHttpRequest;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.BodyInserter;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import reactor.core.publisher.Mono;

import java.util.Collections;
import java.util.Map;
import java.util.Objects;

@Slf4j
@Service
@RequiredArgsConstructor
public class WebClientService {

    @Qualifier("webClient")
    private final WebClient webClient;
    private final ExceptionConverter exceptionConverter;

    public <T> ResponseResult<T> getRequest(
            String url,
            Class<T> responseType
    ) {
        return getRequest(url, responseType, MediaType.APPLICATION_JSON);
    }

    public <T> ResponseResult<T> getRequest(
            String url,
            Class<T> responseType,
            MediaType mediaType
    ) {
        log.debug("======= [getRequest] uri = {}, responseType = {}, headers: = {}, mediaType = {}",
                url, responseType, mediaType);
        try {
            T result = webClient.get()
                    .uri(url)
                    .accept(mediaType)
                    .exchangeToMono(response -> {
                        if (response.statusCode().is2xxSuccessful()) {
                            return response.bodyToMono(responseType);
                        } else {
                            return response.createException().flatMap(Mono::error);
                        }
                    })
                    .block();
            return ResponseResult.success(result);
        } catch (WebClientResponseException exception) {
            return ResponseResult.failure(exceptionConverter.getErrorResponse(exception));
        }
    }


    public <T, V> ResponseResult<V> postRequest(String uri, T requestModel, Class<V> responseType) {
        return postRequest(uri, requestModel, responseType, Collections.emptyMap());
    }

    public <V> ResponseResult<V> postRequest(String uri, Class<V> responseType) {
        return postRequest(uri, null, responseType, Collections.emptyMap());
    }

    public <V> ResponseResult<V> postRequest(String uri, Class<V> responseType, Map<String, String> httpHeadersMap) {
        return postRequest(uri, null, responseType, httpHeadersMap);
    }

    public <T, V> ResponseResult<V> postRequest(
            String uri,
            T requestModel,
            Class<V> responseType,
            MediaType mediaType
    ) {
        return postRequest(uri, requestModel, responseType, null, mediaType);
    }

    public <T, V> ResponseResult<V> postRequest(
            String uri,
            T requestModel,
            Class<V> responseType,
            Map<String, String> httpHeadersMap
    ) {
        return postRequest(uri, requestModel, responseType, httpHeadersMap, MediaType.APPLICATION_JSON);
    }

    public <T, V> ResponseResult<V> postRequest(
            String uri,
            T requestModel,
            Class<V> responseType,
            Map<String, String> httpHeadersMap,
            MediaType mediaType
    ) {
        log.debug("======= [postRequest] requestModel = {}, uri = {}, responseType = {}, headers: = {}, mediaType = {}",
                requestModel, uri, responseType, httpHeadersMap, mediaType);
        try {
            WebClient.RequestBodySpec spec = webClient.post()
                    .uri(uri);
            return getResponseResult(spec, requestModel, mediaType, responseType);
        } catch (WebClientResponseException exception) {
            return ResponseResult.failure(exceptionConverter.getErrorResponse(exception));
        }
    }

    public <T, V> ResponseResult<V> patchRequest(String uri, T requestModel, Class<V> responseType) {
        return patchRequest(uri, requestModel, responseType, Collections.emptyMap());
    }

    public <V> ResponseResult<V> patchRequest(String uri, Class<V> responseType) {
        return patchRequest(uri, null, responseType, Collections.emptyMap());
    }

    public <V> ResponseResult<V> patchRequest(String uri, Class<V> responseType, Map<String, String> httpHeadersMap) {
        return patchRequest(uri, null, responseType, httpHeadersMap);
    }

    public <T, V> ResponseResult<V> patchRequest(
            String uri,
            T requestModel,
            Class<V> responseType,
            Map<String, String> httpHeadersMap) {
        return patchRequest(uri, requestModel, responseType, httpHeadersMap, MediaType.APPLICATION_JSON);
    }

    public <T, V> ResponseResult<V> patchRequest(
            String uri,
            T requestModel,
            Class<V> responseType,
            Map<String, String> httpHeadersMap,
            MediaType mediaType
    ) {
        log.debug("======= [patchRequest] requestModel = {}, uri = {}, responseType = {}, headers: = {}, mediaType = {}",
                requestModel, uri, responseType, httpHeadersMap, mediaType);
        try {
            WebClient.RequestBodySpec spec = webClient.patch()
                    .uri(uri)
                    .headers(headers -> httpHeadersMap.forEach(headers::add));
            return getResponseResult(spec, requestModel, mediaType, responseType);
        } catch (WebClientResponseException exception) {
            return ResponseResult.failure(exceptionConverter.getErrorResponse(exception));
        }
    }

    public <T, V> ResponseResult<V> putRequest(String uri, T requestModel, Class<V> responseType) {
        return putRequest(uri, requestModel, responseType, Collections.emptyMap());
    }

    public <V> ResponseResult<V> putRequest(String uri, Class<V> responseType) {
        return putRequest(uri, null, responseType, Collections.emptyMap());
    }

    public <V> ResponseResult<V> putRequest(String uri, Class<V> responseType, Map<String, String> httpHeadersMap) {
        return putRequest(uri, null, responseType, httpHeadersMap);
    }

    public <T, V> ResponseResult<V> putRequest(String uri, T requestModel, Class<V> responseType, MediaType mediaType) {
        return putRequest(uri, requestModel, responseType, Collections.emptyMap(), mediaType);
    }

    public <T, V> ResponseResult<V> putRequest(
            String uri,
            T requestModel,
            Class<V> responseType,
            Map<String, String> httpHeadersMap
    ) {
        return putRequest(uri, requestModel, responseType, httpHeadersMap, MediaType.APPLICATION_JSON);
    }

    public <T, V> ResponseResult<V> putRequest(
            String uri,
            T requestModel,
            Class<V> responseType,
            Map<String, String> httpHeadersMap,
            MediaType mediaType
    ) {
        log.debug("======= [putRequest] requestModel = {}, uri = {}, responseType = {}, headers: = {}, mediaType = {}",
                requestModel, uri, responseType, httpHeadersMap, mediaType);
        try {
            WebClient.RequestBodySpec spec = webClient.put()
                    .uri(uri)
                    .headers(headers -> httpHeadersMap.forEach(headers::add));
            return getResponseResult(spec, requestModel, mediaType, responseType);
        } catch (WebClientResponseException exception) {
            return ResponseResult.failure(exceptionConverter.getErrorResponse(exception));
        }
    }


    public ResponseResult<ResponseEntity<Void>> deleteRequest(String uri) {
        log.debug("======= [deleteRequest] uri = {}", uri);
        try {
            ResponseEntity<Void> responseEntity = webClient.delete()
                    .uri(uri)
                    .exchangeToMono(response -> {
                        if (response.statusCode().is2xxSuccessful()) {
                            return response.bodyToMono(ResponseEntity.class);
                        } else {
                            return response.createException().flatMap(Mono::error);
                        }
                    })
                    .block();
            return ResponseResult.success(responseEntity);
        } catch (WebClientResponseException exception) {
            return ResponseResult.failure(exceptionConverter.getErrorResponse(exception));
        }
    }

    private <T, V> ResponseResult<V> getResponseResult(
            WebClient.RequestBodySpec spec,
            T body,
            MediaType mediaType,
            Class<V> responseType
    ) {
        addBodyToSpec(spec, body, mediaType);
        V result = spec.exchangeToMono(response -> {
                    if (response.statusCode().is2xxSuccessful()) {
                        return response.bodyToMono(responseType);
                    } else {
                        return response.createException().flatMap(Mono::error);
                    }
                })
                .block();
        return ResponseResult.success(result);
    }

    private <T> void addBodyToSpec(WebClient.RequestBodySpec spec, T body, MediaType mediaType) {
        if (Objects.nonNull(body)) {
            spec.contentType(mediaType);
            if (body instanceof BodyInserter) {
                spec.body((BodyInserter<?, ? super ClientHttpRequest>) body);
            } else spec.bodyValue(body);
        }
    }
}
