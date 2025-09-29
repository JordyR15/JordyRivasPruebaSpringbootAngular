package org.example.pruebaspring.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PeliculaSalaResponseDto {
    private Integer id_pelicula;
    private String nombre_pelicula;
    private Integer duracion;
    private LocalDate fecha_publicacion;
    private Integer id_sala;
    private String nombre_sala;
}