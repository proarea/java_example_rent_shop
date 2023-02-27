package com.snowboard_rental_crm.auth_core.controller;

import com.snowboard_rental_crm.auth_core.service.AuthService;
import com.snowboard_rental_crm.auth_data.model.AuthTokenModel;
import com.snowboard_rental_crm.auth_data.model.RegistrationRequest;
import com.snowboard_rental_crm.auth_data.model.RegistrationResponse;
import com.snowboard_rental_crm.shared_data.model.UserDataResponseModel;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/registration")
    public RegistrationResponse register(@RequestBody RegistrationRequest request) {
        return authService.register(request);
    }

    @GetMapping("/{userId}/tokens")

    public AuthTokenModel getToken(@PathVariable Long userId) {
        return authService.getToken(userId);
    }

    @PostMapping("/{userId}/tokens")
    public ResponseEntity<Void> saveToken(
            @PathVariable Long userId,
            @RequestBody AuthTokenModel request
    ) {
        authService.saveToken(request, userId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/search")
    public UserDataResponseModel getUserByLogin(@RequestParam String login) {
        return authService.getUserByLogin(login);
    }

}
