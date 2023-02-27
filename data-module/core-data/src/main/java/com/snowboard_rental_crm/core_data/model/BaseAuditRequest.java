package com.snowboard_rental_crm.core_data.model;


import com.snowboard_rental_crm.shared_data.enumeration.UserType;
import io.swagger.v3.oas.annotations.Hidden;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public abstract class BaseAuditRequest {

    @Hidden
    private UserType userType;

    @Hidden
    private Long userId;

    public void setAuditFields(Long userId, UserType userType) {
        this.userId = userId;
        this.userType = userType;
    }
}
