package com.snowboard_rental_crm.auth_data.constant;

public class AuthConstants {

    public static final String HEADER_PARAM_JWT_TOKEN = "Authorization";
    public static final String TOKEN_TYPE_CLAIM = "tokenType";
    public static final String USER_ID_ATTRIBUTE = "userId";
    public static final String LOGIN_PARAM = "login";
    public static final String TOKEN_PATH = "/{userId}/tokens";
    public static final String USER_SEARCH_PATH = "/search";

    public static final String REGISTRATION_PATH = "/registration";


    private AuthConstants() {
    }
}
