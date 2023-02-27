package com.snowboard_rental_crm.auth_data.model;

import lombok.Data;
import lombok.Getter;

@Data
@Getter
public class PermissionModel {
    private String permissionName;
    private Permission permission;
}
