package com.example.nnpda_semestralka_b.dto.user;

import lombok.Getter;
import lombok.Setter;

public class NewPasswordDto {
    @Getter
    @Setter
    private String token;

    @Getter
    @Setter
    private String password;
}
