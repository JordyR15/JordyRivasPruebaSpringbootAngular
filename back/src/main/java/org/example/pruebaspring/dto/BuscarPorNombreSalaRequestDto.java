package org.example.pruebaspring.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
public class BuscarPorNombreSalaRequestDto {
    @NotBlank(message = "El nombre de la sala no puede estar vacío")
    private String nombreSala;
}