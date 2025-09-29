package org.example.pruebaspring.services;

import org.example.pruebaspring.dto.*;
import org.example.pruebaspring.entity.Pelicula;
import org.example.pruebaspring.entity.PeliculaSalacine; // <-- AÑADE ESTE IMPORT
import java.util.List;
import java.util.Optional;

public interface IPeliculaService {
    Pelicula createPelicula(CreatePeliculaRequestDto requestDto);

    List<Pelicula> getAllPeliculas();

    Optional<Pelicula> updatePelicula(UpdatePeliculaRequestDto requestDto);

    boolean deletePelicula(DeletePeliculaRequestDto requestDto);

    List<PeliculaSalaResponseDto> buscarPeliculaPorNombreYSala(BuscarPeliculaPorNombreYSalaRequestDto requestDto);

    // --- INICIO DE CAMBIOS ---
    PeliculaSalacine asignarPeliculaASala(AsignarPeliculaSalaRequestDto requestDto);
    // --- FIN DE CAMBIOS ---

    PeliculasPorFechaResponseDto buscarPorFechaPublicacion(BuscarPorFechaRequestDto requestDto);

}