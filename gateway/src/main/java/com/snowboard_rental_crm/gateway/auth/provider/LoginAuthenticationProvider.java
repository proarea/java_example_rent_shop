package com.snowboard_rental_crm.gateway.auth.provider;

import com.snowboard_rental_crm.auth_client.AuthClient;
import com.snowboard_rental_crm.auth_data.model.AuthModel;
import com.snowboard_rental_crm.gateway.auth.config.BCryptEncoder;
import com.snowboard_rental_crm.gateway.auth.util.CustomUserDetails;
import com.snowboard_rental_crm.gateway.auth.util.CustomUsernamePasswordAuthenticationToken;
import com.snowboard_rental_crm.shared_data.model.UserDataResponseModel;
import com.snowboard_rental_crm.shared_module.service.ResponseUnwrapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;


@Slf4j
@Service
@RequiredArgsConstructor
public class LoginAuthenticationProvider implements AuthenticationProvider {

    private final BCryptEncoder encoder;
    private final ResponseUnwrapper responseUnwrapper;
    private final AuthClient authClient;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        log.debug("Attempting to verify user credentials");

        Assert.notNull(authentication, "No authentication data provided");

        CustomUsernamePasswordAuthenticationToken authenticationToken = (CustomUsernamePasswordAuthenticationToken) authentication;

        UserDataResponseModel user = getUser(authentication);
        validateUser(authenticationToken.getAuthModel(), user);

        return buildToken(user);
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(CustomUsernamePasswordAuthenticationToken.class);
    }

    public UserDataResponseModel getUser(Authentication authentication) {
        CustomUsernamePasswordAuthenticationToken authenticationToken = (CustomUsernamePasswordAuthenticationToken) authentication;

        final AuthModel authModel = authenticationToken.getAuthModel();

        return responseUnwrapperUser(authModel);
    }

    public void validateUser(AuthModel authModel, UserDataResponseModel user) {
        if (!encoder.matches(authModel.getPassword(), user.getPassword())) {
            throw new AuthenticationServiceException("Login or password are not valid.");
        }
    }

    public UsernamePasswordAuthenticationToken buildToken(UserDataResponseModel user) {
        CustomUserDetails userDetails = new CustomUserDetails(user);

        CustomUsernamePasswordAuthenticationToken token = new CustomUsernamePasswordAuthenticationToken(user.getId());

        token.setDetails(userDetails);
        return token;
    }

    private UserDataResponseModel responseUnwrapperUser(AuthModel authModel) {
        return responseUnwrapper.unwrapOrThrowException
                (
                        authClient.getUserByLogin(authModel.getLogin()),
                        errorMessage -> {
                            throw new BadCredentialsException
                                    (
                                            String.format("User with login '%s' does not exist", authModel)
                                    );
                        }
                );
    }
}
