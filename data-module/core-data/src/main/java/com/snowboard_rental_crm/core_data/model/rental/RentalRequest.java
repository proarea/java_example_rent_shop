package com.snowboard_rental_crm.core_data.model.rental;

import com.snowboard_rental_crm.core_data.model.BaseAuditRequest;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RentalRequest extends BaseAuditRequest {
    private Long snowboardId;
    private Long maskId;
    private String documentInfo;
}
