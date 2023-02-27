package com.snowboard_rental_crm.gateway.service;

import com.snowboard_rental_crm.auth_client.AuthClient;
import com.snowboard_rental_crm.auth_data.model.AuthTokenModel;
import com.snowboard_rental_crm.auth_data.model.RegistrationRequest;
import com.snowboard_rental_crm.auth_data.model.RegistrationResponse;
import com.snowboard_rental_crm.gateway.auth.config.BCryptEncoder;
import com.snowboard_rental_crm.gateway.auth.util.CustomUserDetails;
import com.snowboard_rental_crm.gateway.auth.util.JwtTokenUtils;
import com.snowboard_rental_crm.shared_module.service.ResponseUnwrapper;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final ResponseUnwrapper responseUnwrapper;
    private final JwtTokenUtils jwtTokenUtils;
    private final AuthClient authClient;
    private final BCryptEncoder encoder;

    public AuthTokenModel register(RegistrationRequest request) {
        RegistrationResponse response = responseUnwrapper.unwrapOrThrowException(
                authClient.register(request)
        );

        CustomUserDetails userDetails = new CustomUserDetails(
                response.getUserId(),
                request.getLogin()
        );

        String accessToken = jwtTokenUtils.generateToken(userDetails).getToken();
        String refreshToken = jwtTokenUtils.generateRefreshToken(userDetails).getToken();

        AuthTokenModel authTokenModel = new AuthTokenModel()
                .setAccessToken(encoder.encodeToken(accessToken))
                .setRefreshToken(encoder.encodeToken(refreshToken));

        responseUnwrapper.unwrapOrThrowException(
                authClient.saveToken(response.getUserId(), authTokenModel),
                errorMessage -> {
                    throw new BadCredentialsException(
                            String.format("Can't save token '%s'", authTokenModel.getAccessToken())
                    );
                }
        );

        return new AuthTokenModel(accessToken, refreshToken);
    }
}
