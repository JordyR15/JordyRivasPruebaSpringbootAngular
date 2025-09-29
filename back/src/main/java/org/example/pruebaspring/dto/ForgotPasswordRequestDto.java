package org.example.pruebaspring.dto;

import lombok.Data;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Data
public class ForgotPasswordRequestDto {
    @NotBlank(message = "El email no puede estar vacío")
    @Email(message = "El formato del email no es válido")
    private String email;
}
    