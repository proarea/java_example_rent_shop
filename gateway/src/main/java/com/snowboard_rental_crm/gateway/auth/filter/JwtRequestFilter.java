package com.snowboard_rental_crm.gateway.auth.filter;


import com.snowboard_rental_crm.auth_data.constant.AuthConstants;
import com.snowboard_rental_crm.gateway.auth.util.CustomUserDetails;
import com.snowboard_rental_crm.gateway.auth.util.JwtAuthenticationToken;
import com.snowboard_rental_crm.gateway.auth.util.TokenExtractor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.util.matcher.RequestMatcher;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
public class JwtRequestFilter extends AbstractAuthenticationProcessingFilter {

    private static final String HEADER_PARAM_JWT_TOKEN = "Authorization";
    private final AuthenticationFailureHandler failureHandler;
    private final TokenExtractor tokenExtractor;

    public JwtRequestFilter(AuthenticationFailureHandler failureHandler, TokenExtractor tokenExtractor, RequestMatcher matcher) {
        super(matcher);
        this.failureHandler = failureHandler;
        this.tokenExtractor = tokenExtractor;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        log.debug("Attempting to authenticate in JWT processing filter");
        String authorizationHeader = request.getHeader(HEADER_PARAM_JWT_TOKEN);
        String jwt = tokenExtractor.extract(authorizationHeader);

        return getAuthenticationManager().authenticate(new JwtAuthenticationToken(jwt));
    }

    @Override
    protected void successfulAuthentication(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain chain,
            Authentication authResult
    ) throws IOException, ServletException {
        SecurityContext context = SecurityContextHolder.createEmptyContext();
        context.setAuthentication(authResult);
        SecurityContextHolder.setContext(context);
        JwtAuthenticationToken token = (JwtAuthenticationToken) authResult;
        CustomUserDetails details = (CustomUserDetails) token.getPrincipal();
        request.setAttribute(AuthConstants.USER_ID_ATTRIBUTE, details.getUserId());

        chain.doFilter(request, response);
    }

    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response,
                                              AuthenticationException failed) throws IOException, ServletException {

        log.debug("UN - Successful JWT Authentication: %s", failed.getMessage());
        failed.printStackTrace();
        SecurityContextHolder.clearContext();
        failureHandler.onAuthenticationFailure(request, response, failed);
    }

}
