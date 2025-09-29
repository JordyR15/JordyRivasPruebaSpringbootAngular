package org.example.pruebaspring.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
public class CreatePeliculaRequestDto {

    @NotBlank(message = "El nombre de la película no puede estar vacío")
    private String nombre;

    private Integer duracion;
}