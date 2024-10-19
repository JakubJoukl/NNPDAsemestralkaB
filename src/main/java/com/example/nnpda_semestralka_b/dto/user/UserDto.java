package com.example.nnpda_semestralka_b.dto.user;

import lombok.Getter;
import lombok.Setter;

public class UserDto {
    @Getter
    @Setter
    public Integer id;

    @Getter
    @Setter
    private String username;

    @Getter
    @Setter
    private String email;
}
