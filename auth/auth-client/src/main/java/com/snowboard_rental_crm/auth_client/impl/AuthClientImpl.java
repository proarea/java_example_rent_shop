package com.snowboard_rental_crm.auth_client.impl;


import com.snowboard_rental_crm.auth_client.AuthClient;
import com.snowboard_rental_crm.auth_data.model.AuthTokenModel;
import com.snowboard_rental_crm.auth_data.model.RegistrationRequest;
import com.snowboard_rental_crm.auth_data.model.RegistrationResponse;
import com.snowboard_rental_crm.shared_data.model.UserDataResponseModel;
import com.snowboard_rental_crm.shared_data.result.ResponseResult;
import com.snowboard_rental_crm.shared_module.service.WebClientService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.util.Collections;

import static com.snowboard_rental_crm.auth_data.constant.AuthConstants.LOGIN_PARAM;
import static com.snowboard_rental_crm.auth_data.constant.AuthConstants.REGISTRATION_PATH;
import static com.snowboard_rental_crm.auth_data.constant.AuthConstants.TOKEN_PATH;
import static com.snowboard_rental_crm.auth_data.constant.AuthConstants.USER_SEARCH_PATH;
import static com.snowboard_rental_crm.shared_module.util.UriComponentsBuilderUtil.buildUrl;


@Component
@RequiredArgsConstructor
public class AuthClientImpl implements AuthClient {

    private final WebClientService webClientService;
    @Value("${auth.url}")
    private String baseUrl;

    @Override
    public ResponseResult<AuthTokenModel> getToken(Long userId) {
        return webClientService.getRequest(
                buildUrl(baseUrl, TOKEN_PATH, userId),
                AuthTokenModel.class
        );
    }

    @Override
    public ResponseResult<ResponseEntity> saveToken(Long userId, AuthTokenModel request) {
        return webClientService.postRequest(
                buildUrl(baseUrl, TOKEN_PATH, userId),
                request,
                ResponseEntity.class
        );
    }

    @Override
    public ResponseResult<UserDataResponseModel> getUserByLogin(String login) {
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.put(LOGIN_PARAM, Collections.singletonList(login));

        return webClientService.getRequest(
                buildUrl(baseUrl, USER_SEARCH_PATH, params),
                UserDataResponseModel.class
        );
    }

    @Override
    public ResponseResult<RegistrationResponse> register(RegistrationRequest registration) {
        return webClientService.postRequest(
                buildUrl(baseUrl, REGISTRATION_PATH),
                registration,
                RegistrationResponse.class
        );
    }
}
