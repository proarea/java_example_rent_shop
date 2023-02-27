package com.snowboard_rental_crm.auth_data.model;


import com.snowboard_rental_crm.auth_data.enumeration.TokenType;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class TokenModel {
    private Long userId;
    private String login;
    private String token;
    private TokenType tokenType;
}
