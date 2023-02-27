package com.snowboard_rental_crm.shared_data.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserDataResponseModel {
    private Long id;
    private String login;
    private String password;
    private String role;
}
