package com.example.nnpda_semestralka_b.dto.user;

import lombok.Getter;
import lombok.Setter;

public class RegistrationDto {
    @Getter
    @Setter
    private String username;

    @Getter
    @Setter
    private String password;

    @Getter
    @Setter
    private String email;
}
