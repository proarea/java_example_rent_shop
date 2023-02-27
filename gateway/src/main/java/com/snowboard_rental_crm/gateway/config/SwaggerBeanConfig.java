package com.snowboard_rental_crm.gateway.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;

import java.util.List;

@Configuration
public class SwaggerBeanConfig {

    public SwaggerBeanConfig(MappingJackson2HttpMessageConverter converter) {
        converter.setSupportedMediaTypes(List.of(MediaType.APPLICATION_JSON, MediaType.APPLICATION_OCTET_STREAM));
    }
}
