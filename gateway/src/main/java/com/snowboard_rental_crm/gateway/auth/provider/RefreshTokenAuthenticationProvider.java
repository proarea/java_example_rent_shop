package com.snowboard_rental_crm.gateway.auth.provider;


import com.snowboard_rental_crm.auth_client.AuthClient;
import com.snowboard_rental_crm.auth_data.enumeration.TokenType;
import com.snowboard_rental_crm.auth_data.model.AuthTokenModel;
import com.snowboard_rental_crm.auth_data.model.TokenModel;
import com.snowboard_rental_crm.gateway.auth.config.BCryptEncoder;
import com.snowboard_rental_crm.gateway.auth.util.CustomUserDetails;
import com.snowboard_rental_crm.gateway.auth.util.JwtTokenUtils;
import com.snowboard_rental_crm.gateway.auth.util.RefreshTokenAuthenticationToken;
import com.snowboard_rental_crm.shared_data.model.UserDataResponseModel;
import com.snowboard_rental_crm.shared_module.service.ResponseUnwrapper;
import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.Objects;

import static com.snowboard_rental_crm.auth_data.constant.AuthConstants.TOKEN_TYPE_CLAIM;
import static com.snowboard_rental_crm.auth_data.constant.ExceptionConstants.INVALID_TOKEN;
import static com.snowboard_rental_crm.auth_data.constant.ExceptionConstants.INVALID_USER_ID;
import static com.snowboard_rental_crm.auth_data.constant.ExceptionConstants.TOKEN_NOT_EXIST;
import static com.snowboard_rental_crm.auth_data.constant.ExceptionConstants.USER_NOT_EXIST;


@Component
@Slf4j
@RequiredArgsConstructor
public class RefreshTokenAuthenticationProvider implements AuthenticationProvider {

    private final JwtTokenUtils jwtTokenUtils;
    private final ResponseUnwrapper responseUnwrapper;
    private final AuthClient authClient;
    private final BCryptEncoder encoder;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        log.debug("Attempting to parse token");

        RefreshTokenAuthenticationToken refreshAuthenticationToken = (RefreshTokenAuthenticationToken) authentication;
        TokenModel tokenModel = buildRefreshTokenModel(refreshAuthenticationToken);

        validateAuthFilterToken(tokenModel);

        UserDataResponseModel user = getUser(tokenModel);
        validateUser(user, tokenModel);

        return buildToken(user);
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(RefreshTokenAuthenticationToken.class);
    }

    private TokenModel buildRefreshTokenModel(RefreshTokenAuthenticationToken refreshAuthenticationToken) {
        String token = (String) refreshAuthenticationToken.getCredentials();
        Claims claims = jwtTokenUtils.parseToken(token);

        return TokenModel.builder()
                .tokenType(TokenType.valueOf((String) claims.get(TOKEN_TYPE_CLAIM)))
                .userId(Long.valueOf(claims.getId()))
                .login(claims.getSubject())
                .token(token)
                .build();
    }

    public void validateAuthFilterToken(TokenModel model) {
        AuthTokenModel auth = responseUnwrapper.unwrapOrThrowException(
                authClient.getToken(model.getUserId()),
                errorMessage -> {
                    throw new BadCredentialsException(TOKEN_NOT_EXIST);
                }
        );

        if (!encoder.matchTokens(model.getToken(), auth.getRefreshToken())) {
            throw new BadCredentialsException(INVALID_TOKEN);
        }

        if (!Objects.equals(model.getTokenType(), TokenType.REFRESH_TOKEN)) {
            throw new BadCredentialsException(INVALID_TOKEN);
        }
    }

    public UserDataResponseModel getUser(TokenModel model) {
        return responseUnwrapper.unwrapOrThrowException(
                authClient.getUserByLogin(model.getLogin()),
                errorMessage -> {
                    throw new BadCredentialsException(String.format(USER_NOT_EXIST, model.getLogin()));
                });
    }

    public void validateUser(UserDataResponseModel user, TokenModel model) {
        if (Objects.isNull(user)) {
            throw new UsernameNotFoundException(String.format(USER_NOT_EXIST, model.getLogin()));
        }

        if (!Objects.equals(user.getId(), model.getUserId())) {
            throw new UsernameNotFoundException(String.format(INVALID_USER_ID, model.getUserId()));
        }
    }

    public RefreshTokenAuthenticationToken buildToken(UserDataResponseModel user) {
        UserDetails userDetails = new CustomUserDetails(user);

        return new RefreshTokenAuthenticationToken(
                userDetails,
                userDetails.getAuthorities()
        );
    }
}
