package com.snowboard_rental_crm.core_data.enumiration.snowboard;

import lombok.Getter;

@Getter
public enum SnowboardType {
    CAMBER("Camber"),
    ROCKET("Rocket"),
    FLAT("Flat"),
    HYBRID("Hybrid");

    private final String type;

    SnowboardType(String type) {
        this.type = type;
    }
}
