package com.example.nnpda_semestralka_b.config;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GeneralConfig {
    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }
}
