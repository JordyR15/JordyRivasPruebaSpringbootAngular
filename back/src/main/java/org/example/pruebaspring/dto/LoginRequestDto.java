package org.example.pruebaspring.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginRequestDto {
    private String username;
    private String password;
}