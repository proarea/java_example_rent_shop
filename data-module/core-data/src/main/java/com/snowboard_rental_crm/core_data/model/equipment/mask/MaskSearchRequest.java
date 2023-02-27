package com.snowboard_rental_crm.core_data.model.equipment.mask;

import com.snowboard_rental_crm.core_data.enumiration.equipment.LensTint;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MaskSearchRequest {
    private String name;
    private LensTint lensTint;
    private Boolean antiFog;

}
