package org.example.pruebaspring.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@Setter
public class UpdatePeliculaRequestDto {

    @NotNull(message = "El ID de la película no puede ser nulo")
    private Integer id;

    @NotBlank(message = "El nombre de la película no puede estar vacío")
    private String nombre;

    private Integer duracion;
}