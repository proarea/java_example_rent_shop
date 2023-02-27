package com.snowboard_rental_crm.core_data.model.equipment.snowboard;

import com.snowboard_rental_crm.core_data.enumiration.EquipmentClass;
import com.snowboard_rental_crm.core_data.enumiration.snowboard.SnowboardStyle;
import com.snowboard_rental_crm.core_data.enumiration.snowboard.SnowboardType;
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
public class SnowboardRequest extends BaseAuditRequest {
    @NotBlank
    private String name;
    private EquipmentClass equipmentClass;
    private SnowboardType snowboardType;
    private SnowboardStyle snowboardStyle;
    private Long size;
    private BigDecimal collateralValue;

}
