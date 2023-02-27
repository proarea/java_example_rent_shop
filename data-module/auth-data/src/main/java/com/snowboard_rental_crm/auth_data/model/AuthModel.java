package com.snowboard_rental_crm.auth_data.model;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

@Data
public class AuthModel implements Serializable {

    @NotBlank
    private String login;

    @NotBlank
    private String password;

}
