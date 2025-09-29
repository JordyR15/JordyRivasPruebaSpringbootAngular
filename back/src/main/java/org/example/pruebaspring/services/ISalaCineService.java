package org.example.pruebaspring.services;

import org.example.pruebaspring.dto.BuscarPorNombreSalaRequestDto;
import org.example.pruebaspring.dto.CreateSalaCineRequestDto;
import org.example.pruebaspring.entity.Pelicula; // <-- Asegúrate de que este import esté
import org.example.pruebaspring.entity.SalaCine;

import java.util.List;

public interface ISalaCineService {
    SalaCine createSalaCine(CreateSalaCineRequestDto requestDto);

    List<SalaCine> getAllSalas();

    // --- INICIO DE CORRECCIÓN ---
    // El tipo de retorno debe ser List<Pelicula>, no List<PeliculaSalaResponseDto>
    List<Pelicula> buscarPeliculasPorNombreSala(BuscarPorNombreSalaRequestDto requestDto);
    // --- FIN DE CORRECCIÓN ---
}