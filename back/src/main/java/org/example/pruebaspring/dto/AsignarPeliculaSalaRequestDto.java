package org.example.pruebaspring.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Getter
@Setter
public class AsignarPeliculaSalaRequestDto {

    @NotNull(message = "El ID de la película no puede ser nulo")
    private Integer peliculaId;

    @NotNull(message = "El ID de la sala de cine no puede ser nulo")
    private Integer salaCineId;

    @NotNull(message = "La fecha de publicación no puede ser nula")
    private LocalDate fechaPublicacion;

    @NotNull(message = "La fecha de fin no puede ser nula")
    private LocalDate fechaFin;
}