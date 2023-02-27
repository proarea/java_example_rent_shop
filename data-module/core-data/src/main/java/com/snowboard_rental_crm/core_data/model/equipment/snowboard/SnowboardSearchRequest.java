package com.snowboard_rental_crm.core_data.model.equipment.snowboard;

import com.snowboard_rental_crm.core_data.enumiration.EquipmentClass;
import com.snowboard_rental_crm.core_data.enumiration.snowboard.SnowboardStyle;
import com.snowboard_rental_crm.core_data.enumiration.snowboard.SnowboardType;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SnowboardSearchRequest {
    private String name;
    private EquipmentClass equipmentClass;
    private SnowboardType snowboardType;
    private SnowboardStyle snowboardStyle;

}
