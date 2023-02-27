package com.snowboard_rental_crm.shared_module.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

import static com.snowboard_rental_crm.shared_data.constant.SharedConstants.MAX_IN_MEMORY_SIZE;

@Configuration
public class WebClientConfig {

    @Bean
    public WebClient getWebClient() {
        return WebClient.builder()
                .codecs(configurer -> configurer
                        .defaultCodecs()
                        .maxInMemorySize(MAX_IN_MEMORY_SIZE))
                .build();
    }
}
