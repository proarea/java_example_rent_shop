package com.snowboard_rental_crm.gateway.auth.util;

public interface TokenExtractor {

    String extract(String payload);

}
