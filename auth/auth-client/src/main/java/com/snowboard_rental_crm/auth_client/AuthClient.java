package com.snowboard_rental_crm.auth_client;

import com.snowboard_rental_crm.auth_data.model.AuthTokenModel;
import com.snowboard_rental_crm.auth_data.model.RegistrationRequest;
import com.snowboard_rental_crm.auth_data.model.RegistrationResponse;
import com.snowboard_rental_crm.shared_data.model.UserDataResponseModel;
import com.snowboard_rental_crm.shared_data.result.ResponseResult;
import org.springframework.http.ResponseEntity;

public interface AuthClient {

    ResponseResult<AuthTokenModel> getToken(Long userId);

    ResponseResult<ResponseEntity> saveToken(Long userId, AuthTokenModel request);

    ResponseResult<UserDataResponseModel> getUserByLogin(String login);

    ResponseResult<RegistrationResponse> register(RegistrationRequest registration);
}
