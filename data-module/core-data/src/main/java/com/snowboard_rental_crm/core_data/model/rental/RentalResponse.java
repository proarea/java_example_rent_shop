package com.snowboard_rental_crm.core_data.model.rental;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RentalResponse {
    private Long id;
    private Long snowboardId;
    private Long maskId;
    private Long snowboardBoots;
    private BigDecimal collateralValue;
    private String documentInfo;
}
