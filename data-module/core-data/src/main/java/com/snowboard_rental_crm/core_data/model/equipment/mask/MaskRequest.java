package com.snowboard_rental_crm.core_data.model.equipment.mask;

import com.snowboard_rental_crm.core_data.enumiration.equipment.LensTint;
import com.snowboard_rental_crm.core_data.model.BaseAuditRequest;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import java.math.BigDecimal;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MaskRequest extends BaseAuditRequest {
    @NotBlank
    private String name;
    private LensTint lensTint;
    private boolean isAntiFog;
    private BigDecimal collateralValue;
}
