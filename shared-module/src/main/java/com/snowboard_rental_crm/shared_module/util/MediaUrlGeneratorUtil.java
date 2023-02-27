package com.snowboard_rental_crm.shared_module.util;

import lombok.experimental.UtilityClass;

@UtilityClass
public class MediaUrlGeneratorUtil {

    public String generateMediaUrl(String photoType, Long id) {
        return String.format("%s/%s", photoType.toLowerCase(), id);
    }
}