package com.snowboard_rental_crm.shared_module.util;

import lombok.experimental.UtilityClass;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.util.UriComponentsBuilder;

@UtilityClass
public class UriComponentsBuilderUtil {

    public String buildUrl(String baseUrl, String path, Object... ids) {
        return buildUrl(baseUrl, path, new LinkedMultiValueMap<>(), ids);
    }

    public String buildUrl(String baseUrl, Object... ids) {
        return buildUrl(baseUrl, "", new LinkedMultiValueMap<>(), ids);
    }

    public String buildUrl(String baseUrl, MultiValueMap<String, String> params, Object... ids) {
        return buildUrl(baseUrl, "", params, ids);
    }

    public String buildUrl(String baseUrl, String path, MultiValueMap<String, String> params, Object... ids) {
        return UriComponentsBuilder
                .fromUriString(baseUrl)
                .queryParams(params)
                .path(path)
                .buildAndExpand(ids)
                .toString();
    }
}
