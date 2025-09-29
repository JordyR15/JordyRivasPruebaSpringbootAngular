package org.example.pruebaspring.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Getter
@Setter
public class BuscarPorFechaRequestDto {

    @NotNull(message = "La fecha de publicación no puede ser nula")
    private LocalDate fechaPublicacion;
}