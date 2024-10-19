package com.example.nnpda_semestralka_b;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories("com.example.nnpda_semestralka_b.repositories")
public class NnpdaSemestralkaBApplication {

    public static void main(String[] args) {
        SpringApplication.run(NnpdaSemestralkaBApplication.class, args);
    }

}
