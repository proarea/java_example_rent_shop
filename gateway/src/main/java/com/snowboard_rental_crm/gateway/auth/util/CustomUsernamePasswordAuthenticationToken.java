package com.snowboard_rental_crm.gateway.auth.util;

import com.snowboard_rental_crm.auth_data.model.AuthModel;
import lombok.EqualsAndHashCode;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

@EqualsAndHashCode(callSuper = true)
public class CustomUsernamePasswordAuthenticationToken extends UsernamePasswordAuthenticationToken {

    private AuthModel authModel;

    public CustomUsernamePasswordAuthenticationToken(AuthModel authModel) {
        super(null, null);
        this.authModel = authModel;
    }

    public CustomUsernamePasswordAuthenticationToken(
            Object principal) {
        super(principal, null, null);
    }

    public AuthModel getAuthModel() {
        return authModel;
    }
}
