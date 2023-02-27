package com.snowboard_rental_crm.core_data.enumiration.snowboard;

import lombok.Getter;

@Getter
public enum SnowboardStyle {
    ALL_MOUNTAIN("all_mountain"),
    FREERIDE("freeride"),
    FREESTYLE("freestyle");

    private final String style;

    SnowboardStyle(String style) {
        this.style = style;
    }
}
