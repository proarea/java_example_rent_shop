package com.snowboard_rental_crm.shared_data.constant;

public class SharedConstants {

    public static final String TENANT_ID_HEADER = "X-TenantID";
    public static final String USER_ID_PARAM = "userId";
    public static final String USER_TYPE_PARAM = "userType";
    public static final String PAGE_NUMBER = "page";
    public static final String PAGE_SIZE = "size";
    public static final String NOT_FOUND_EXCEPTION_MESSAGE_ID = "%s with id %s does not exist";
    public static final String ORDER_TYPE_PARAM = "orderType";
    public static final int MAX_IN_MEMORY_SIZE = 20 * 1024 * 1024;
    public static final String PHOTO_PARAM = "photo";
    public static final String REQUEST_BODY_PARAM = "request";

    private SharedConstants() {
    }
}
