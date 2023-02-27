package com.snowboard_rental_crm.shared_data.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class WebClientException extends RuntimeException {
    private final ErrorResponse errorResponse;
}
