package com.snowboard_rental_crm.media_core;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "com.snowboard_rental_crm.*")
public class MediaCoreApp {
    public static void main(String[] args) {
        SpringApplication.run(MediaCoreApp.class, args);
    }
}