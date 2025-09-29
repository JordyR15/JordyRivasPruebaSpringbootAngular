package org.example.pruebaspring.dto;

import lombok.Data;
import javax.validation.constraints.NotBlank;

@Data
public class ResetPasswordRequestDto {
    @NotBlank(message = "El token no puede estar vacío")
    private String token;

    @NotBlank(message = "La nueva contraseña no puede estar vacía")
    private String newPassword;
}
    