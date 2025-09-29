package org.example.pruebaspring.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.example.pruebaspring.entity.Pelicula;

import java.util.List;

@Getter
@Setter // <-- Esta anotación es la que faltaba o estaba incorrecta
@NoArgsConstructor
@AllArgsConstructor
public class PeliculasPorFechaResponseDto {
    private int cantidad;
    private List<Pelicula> peliculas;
}