package com.snowboard_rental_crm.core_data.model.equipment.mask;

import com.snowboard_rental_crm.core_data.enumiration.equipment.LensTint;
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
public class MaskResponse {
    private Long id;
    private String name;
    private LensTint lensTint;
    private boolean antiFog;
    private BigDecimal collateralValue;
}
