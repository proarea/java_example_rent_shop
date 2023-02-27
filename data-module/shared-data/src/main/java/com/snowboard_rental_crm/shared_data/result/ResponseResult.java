package com.snowboard_rental_crm.shared_data.result;


import com.snowboard_rental_crm.shared_data.exception.ErrorResponse;
import lombok.Data;

@Data
public class ResponseResult<T> {

    private ResultStatus resultStatus;
    private T response;
    private ErrorResponse errorResponse;

    public static <T> ResponseResult<T> success(T response) {
        ResponseResult<T> result = new ResponseResult<>();
        result.setResultStatus(ResultStatus.SUCCESS);
        result.setResponse(response);
        return result;
    }

    public static <T> ResponseResult<T> failure(ErrorResponse errorResponse) {
        ResponseResult<T> result = new ResponseResult<>();
        result.setResultStatus(ResultStatus.FAILURE);
        result.setErrorResponse(errorResponse);
        return result;
    }
}
