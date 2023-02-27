package com.snowboard_rental_crm.gateway.auth.filter;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.snowboard_rental_crm.auth_data.model.AuthModel;
import com.snowboard_rental_crm.gateway.auth.util.AuthMethodNotSupportedException;
import com.snowboard_rental_crm.gateway.auth.util.CustomUsernamePasswordAuthenticationToken;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
public class LoginRequestFilter extends AbstractAuthenticationProcessingFilter {
    private final ObjectMapper objectMapper;
    private final AuthenticationSuccessHandler successHandler;
    private final AuthenticationFailureHandler failureHandler;


    public LoginRequestFilter(
            String defaultProcessUrl,
            @Qualifier("loginAdminAuthSuccessHandler") AuthenticationSuccessHandler successHandler,
            AuthenticationFailureHandler failureHandler,
            ObjectMapper objectMapper

    ) {
        super(defaultProcessUrl);
        this.successHandler = successHandler;
        this.objectMapper = objectMapper;
        this.failureHandler = failureHandler;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
            throws AuthenticationException {
        validateRequest(request);

        AbstractAuthenticationToken authenticationToken = buildAuthFilterToken(request);
        return this.getAuthenticationManager().authenticate(authenticationToken);
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain,
                                            Authentication authResult) throws IOException, ServletException {
        log.debug("Authentication Successful");
        getAuthSuccessHandler().onAuthenticationSuccess(request, response, authResult);
    }

    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response,
                                              AuthenticationException failed) throws IOException, ServletException {
        log.error("Authentication Failed");
        SecurityContextHolder.clearContext();
        getAuthFailureHandler().onAuthenticationFailure(request, response, failed);
    }

    public void validateRequest(HttpServletRequest request) {
        if (!HttpMethod.POST.name().equals(request.getMethod())) {
            throw new AuthMethodNotSupportedException("Authentication method not supported");
        }
    }

    public AuthenticationSuccessHandler getAuthSuccessHandler() {
        return successHandler;
    }

    public AuthenticationFailureHandler getAuthFailureHandler() {
        return failureHandler;
    }

    @SneakyThrows(IOException.class)
    public AbstractAuthenticationToken buildAuthFilterToken(HttpServletRequest request) {
        AuthModel authModel = objectMapper.readValue(request.getReader(), AuthModel.class);

        return new CustomUsernamePasswordAuthenticationToken(
                authModel
        );
    }

}
