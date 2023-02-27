package com.snowboard_rental_crm.shared_module.util;

import lombok.experimental.UtilityClass;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.util.Collections;

@UtilityClass
public class RequestParamBuilderUtil {
    public MultiValueMap<String, String> buildRequestParam(String nameParam, String param) {
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.put(nameParam, Collections.singletonList(param));
        return params;
    }

}
