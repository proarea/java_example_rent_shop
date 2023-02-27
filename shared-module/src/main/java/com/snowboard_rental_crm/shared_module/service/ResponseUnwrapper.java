package com.snowboard_rental_crm.shared_module.service;


import com.snowboard_rental_crm.shared_data.exception.ErrorResponse;
import com.snowboard_rental_crm.shared_data.exception.WebClientException;
import com.snowboard_rental_crm.shared_data.result.ResponseResult;
import com.snowboard_rental_crm.shared_data.result.ResultStatus;
import org.springframework.stereotype.Service;

import java.util.function.Consumer;

@Service
public class ResponseUnwrapper {

    public <V> V unwrapOrThrowException(ResponseResult<V> responseResult) {
        return unwrapOrThrowException(responseResult, errorResponse -> {
            throw new WebClientException(errorResponse);
        });
    }

    public <V> V unwrapOrThrowException(ResponseResult<V> responseResult, Consumer<ErrorResponse> exceptionConsumer) {
        if (responseResult.getResultStatus() == ResultStatus.FAILURE) {
            exceptionConsumer.accept(responseResult.getErrorResponse());
        }
        return responseResult.getResponse();
    }
}
