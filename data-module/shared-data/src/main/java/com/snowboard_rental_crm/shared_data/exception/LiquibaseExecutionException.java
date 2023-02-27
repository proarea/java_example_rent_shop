package com.snowboard_rental_crm.shared_data.exception;

public class LiquibaseExecutionException extends RuntimeException {
    public LiquibaseExecutionException(String message) {
        super(message);
    }
}
