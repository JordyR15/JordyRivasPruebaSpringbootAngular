package org.example.pruebaspring.dto;

import lombok.Getter;
import lombok.Setter;

// Se eliminan las importaciones de javax.validation ya que no se usan.

@Getter
@Setter
public class BuscarPeliculaPorNombreYSalaRequestDto {

    // Se quita @NotBlank para permitir búsquedas sin nombre de película
    private String nombrePelicula;

    // Se quita @NotNull para permitir búsquedas sin ID de sala
    private Integer idSala;
}