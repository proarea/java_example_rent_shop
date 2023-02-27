package com.snowboard_rental_crm.auth_data.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class Permission {
    private boolean read;
    private boolean write;
    private boolean edit;
    private boolean delete;

}
