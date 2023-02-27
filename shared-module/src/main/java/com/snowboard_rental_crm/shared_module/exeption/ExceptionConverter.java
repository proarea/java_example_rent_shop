package com.snowboard_rental_crm.shared_module.exeption;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.snowboard_rental_crm.shared_data.exception.ErrorResponse;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClientResponseException;

@Component
@RequiredArgsConstructor
public class ExceptionConverter {

    private final ObjectMapper mapper;

    @SneakyThrows(JsonProcessingException.class)
    public ErrorResponse getErrorResponse(WebClientResponseException ex) {
        return mapper.readValue(ex.getResponseBodyAsString(), ErrorResponse.class);
    }
}
