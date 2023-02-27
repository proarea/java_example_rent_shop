package com.snowboard_rental_crm.gateway.auth.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.snowboard_rental_crm.auth_client.AuthClient;
import com.snowboard_rental_crm.auth_data.model.AuthTokenModel;
import com.snowboard_rental_crm.gateway.auth.config.BCryptEncoder;
import com.snowboard_rental_crm.gateway.auth.util.CustomUserDetails;
import com.snowboard_rental_crm.gateway.auth.util.JwtToken;
import com.snowboard_rental_crm.gateway.auth.util.JwtTokenUtils;
import com.snowboard_rental_crm.gateway.auth.util.RefreshTokenAuthenticationToken;
import com.snowboard_rental_crm.shared_module.service.ResponseUnwrapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.WebAttributes;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@Slf4j
@Component
@RequiredArgsConstructor
public class RefreshTokenAuthSuccessHandler implements AuthenticationSuccessHandler {

    private final JwtTokenUtils jwtTokenUtils;
    private final ObjectMapper objectMapper;
    private final ResponseUnwrapper responseUnwrapper;
    private final AuthClient authClient;
    private final BCryptEncoder encoder;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException {
        RefreshTokenAuthenticationToken sourceToken = (RefreshTokenAuthenticationToken) authentication;
        CustomUserDetails authenticationDetails = (CustomUserDetails) sourceToken.getPrincipal();

        JwtToken accessToken = jwtTokenUtils.generateToken(authenticationDetails);
        JwtToken refreshToken = jwtTokenUtils.generateRefreshToken(authenticationDetails);

        AuthTokenModel encodedAuth = AuthTokenModel.builder()
                .accessToken(encoder.encodeToken(accessToken.getToken()))
                .refreshToken(encoder.encodeToken(refreshToken.getToken()))
                .build();

        responseUnwrapperSaveToken(authenticationDetails, encodedAuth);

        log.debug("Generating tokens for authenticated user");
        response.setStatus(HttpStatus.OK.value());
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);

        objectMapper.writeValue(
                response.getWriter(),
                new AuthTokenModel(accessToken.getToken(), refreshToken.getToken())
        );

        clearAuthenticationAttributes(request);
    }

    protected final void clearAuthenticationAttributes(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session == null) {
            return;
        }
        session.removeAttribute(WebAttributes.AUTHENTICATION_EXCEPTION);
    }

    private void responseUnwrapperSaveToken(CustomUserDetails authenticationDetails, AuthTokenModel encodedAuth) {
        responseUnwrapper.unwrapOrThrowException
                (
                        authClient.saveToken(authenticationDetails.getUserId(), encodedAuth),
                        errorMessage -> {
                            throw new BadCredentialsException
                                    (
                                            String.format("Can't save token '%s'", encodedAuth.getAccessToken())
                                    );
                        }
                );
    }

}
