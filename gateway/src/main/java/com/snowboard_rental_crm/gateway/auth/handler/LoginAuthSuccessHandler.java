package com.snowboard_rental_crm.gateway.auth.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.snowboard_rental_crm.auth_client.AuthClient;
import com.snowboard_rental_crm.auth_data.model.AuthTokenModel;
import com.snowboard_rental_crm.gateway.auth.config.BCryptEncoder;
import com.snowboard_rental_crm.gateway.auth.util.CustomUserDetails;
import com.snowboard_rental_crm.gateway.auth.util.JwtToken;
import com.snowboard_rental_crm.gateway.auth.util.JwtTokenUtils;
import com.snowboard_rental_crm.shared_module.service.ResponseUnwrapper;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.WebAttributes;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@Slf4j
@Component
@RequiredArgsConstructor
public class LoginAuthSuccessHandler implements AuthenticationSuccessHandler {

    private final JwtTokenUtils jwtTokenUtils;
    private final ObjectMapper objectMapper;
    private final ResponseUnwrapper responseUnwrapper;
    private final AuthClient authClient;
    private final BCryptEncoder encoder;

    @SneakyThrows
    @Override
    public void onAuthenticationSuccess(
            HttpServletRequest request,
            HttpServletResponse response,
            Authentication authentication
    ) {
        UsernamePasswordAuthenticationToken sourceToken = (UsernamePasswordAuthenticationToken) authentication;
        CustomUserDetails authenticationDetails = (CustomUserDetails) sourceToken.getDetails();

        JwtToken accessToken = jwtTokenUtils.generateToken(authenticationDetails);
        JwtToken refreshToken = jwtTokenUtils.generateRefreshToken(authenticationDetails);

        AuthTokenModel encodedAuth = AuthTokenModel.builder()
                .accessToken(encoder.encodeToken(accessToken.getToken()))
                .refreshToken(encoder.encodeToken(refreshToken.getToken()))
                .build();

        responseUnwrapperSaveToken(authenticationDetails, encodedAuth, accessToken);

        log.debug("Generating tokens for authenticated user");
        log.info("info about: {}", request.getRequestURI());
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

    private void responseUnwrapperSaveToken(
            CustomUserDetails authenticationDetails,
            AuthTokenModel encodedAuth,
            JwtToken token
    ) {
        responseUnwrapper.unwrapOrThrowException
                (
                        authClient.saveToken(authenticationDetails.getUserId(), encodedAuth),
                        errorMessage -> {
                            throw new BadCredentialsException
                                    (
                                            String.format("Can't save token '%s'", token.getToken())
                                    );
                        }
                );
    }
}
