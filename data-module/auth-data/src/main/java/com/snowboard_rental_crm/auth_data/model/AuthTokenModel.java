package com.snowboard_rental_crm.auth_data.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AuthTokenModel {
    private String accessToken;
    private String refreshToken;
}


