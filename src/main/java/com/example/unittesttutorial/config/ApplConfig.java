package com.example.unittesttutorial.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class ApplConfig {

    @Bean
    public RestTemplate restTemplateBean() {
        return new RestTemplate();
    }
}
