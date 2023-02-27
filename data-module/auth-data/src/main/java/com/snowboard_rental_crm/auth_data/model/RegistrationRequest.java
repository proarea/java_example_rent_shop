package com.snowboard_rental_crm.auth_data.model;

import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Data
public class RegistrationRequest {

    @NotBlank
    private String login;

    @Email
    private String email;

    @NotBlank
    private String password;

    @NotBlank
    private String phone;
}
