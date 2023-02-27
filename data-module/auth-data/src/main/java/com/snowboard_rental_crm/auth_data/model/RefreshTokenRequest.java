package com.snowboard_rental_crm.auth_data.model;

import lombok.Data;

@Data
public class RefreshTokenRequest {
    private String refreshToken;
}
