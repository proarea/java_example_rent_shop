package com.snowboard_rental_crm.gateway.controller;

import com.snowboard_rental_crm.auth_data.model.AuthModel;
import com.snowboard_rental_crm.auth_data.model.AuthTokenModel;
import com.snowboard_rental_crm.auth_data.model.RefreshTokenRequest;
import com.snowboard_rental_crm.auth_data.model.RegistrationRequest;
import com.snowboard_rental_crm.gateway.service.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@Tag(name = "Authentication API")
@RestController
@RequestMapping("v1/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    //only to show this rest calls in swagger, all auth logic implemented by AuthFilters
    @PostMapping
    @Operation(description = "Authentication")
    public AuthTokenModel auth(@RequestBody @Valid AuthModel request) {
        return null;
    }

    @PostMapping("/registrations")
    @Operation(description = "Registration")
    public AuthTokenModel registration(@RequestBody @Validated RegistrationRequest request) {
        return authService.register(request);
    }

    @PostMapping("/refresh-token")
    @Operation(description = "Refresh token")
    public AuthTokenModel refreshToken(@RequestBody @Valid RefreshTokenRequest request) {
        return null;
    }
}
