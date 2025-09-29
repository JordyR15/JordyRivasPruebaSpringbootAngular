package org.example.pruebaspring.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@Setter
public class CreateUserRequestDto {
    // Campos para la entidad User
    @NotBlank(message = "El nombre de usuario no puede estar vacío")
    private String username;

    @NotBlank(message = "La contraseña no puede estar vacía")
    private String password;

    // Campos para la entidad Person
    @NotBlank(message = "El nombre no puede estar vacío")
    private String firstName;

    @NotBlank(message = "El apellido no puede estar vacío")
    private String lastName;

    @NotBlank(message = "El email no puede estar vacío")
    @Email(message = "El formato del email no es válido")
    private String email;

    // ID del rol a asignar
    @NotNull(message = "El ID del rol no puede ser nulo")
    private Integer roleId;
}