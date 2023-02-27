package com.snowboard_rental_crm.auth_data.constant;

public class ExceptionConstants {

    public static final String TOKEN_NOT_EXIST = "Token does not exist";
    public static final String USER_NOT_EXIST = "User with login '%s' does not exist";
    public static final String INVALID_USER_ID = "Authentication Failed. Invalid user id: %s";
    public static final String INVALID_TOKEN = "Invalid JWT token";
    public static final String NOT_INIT_STRATEGY = "Not initialized strategy: %s for %s";
    public static final String USER_EXIST = "User with login: %s already exists";
    public static final String LOGIN_EXIST = "Login: %s already exists";
    public static final String EMAIL_EXIST = "Email: %s already exists";
    public static final String PHONE_EXIST = "Phone: %s already exists";


    private ExceptionConstants() {
    }
}
