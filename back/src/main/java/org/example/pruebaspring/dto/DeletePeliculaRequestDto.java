package org.example.pruebaspring.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter
@Setter
public class DeletePeliculaRequestDto {

    @NotNull(message = "El ID de la película no puede ser nulo")
    private Integer id;
}