package org.example.pruebaspring.services.impl;

import org.example.pruebaspring.dto.*;
import org.example.pruebaspring.entity.Pelicula;
import org.example.pruebaspring.entity.PeliculaSalacine;
import org.example.pruebaspring.entity.PeliculaSalacineId;
import org.example.pruebaspring.entity.SalaCine;
import org.example.pruebaspring.repository.PeliculaRepository;
import org.example.pruebaspring.repository.PeliculaSalacineRepository;
import org.example.pruebaspring.repository.SalaCineRepository;
import org.example.pruebaspring.services.IPeliculaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.sql.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PeliculaServiceImpl implements IPeliculaService {

    @Autowired
    private PeliculaRepository peliculaRepository;

    @Autowired
    private SalaCineRepository salaCineRepository;

    @Autowired
    private PeliculaSalacineRepository peliculaSalacineRepository;

    @Override
    public Pelicula createPelicula(CreatePeliculaRequestDto requestDto) {
        Pelicula pelicula = new Pelicula();
        pelicula.setNombre(requestDto.getNombre());
        pelicula.setDuracion(requestDto.getDuracion());
        return peliculaRepository.save(pelicula);
    }

    @Override
    public List<Pelicula> getAllPeliculas() {
        return peliculaRepository.findAll();
    }

    @Override
    public Optional<Pelicula> updatePelicula(UpdatePeliculaRequestDto requestDto) {
        Optional<Pelicula> peliculaOptional = peliculaRepository.findById(requestDto.getId());
        if (peliculaOptional.isPresent()) {
            Pelicula peliculaExistente = peliculaOptional.get();
            peliculaExistente.setNombre(requestDto.getNombre());
            peliculaExistente.setDuracion(requestDto.getDuracion());
            return Optional.of(peliculaRepository.save(peliculaExistente));
        } else {
            return Optional.empty();
        }
    }

    @Override
    public boolean deletePelicula(DeletePeliculaRequestDto requestDto) {
        Optional<Pelicula> peliculaOptional = peliculaRepository.findById(requestDto.getId());
        if (peliculaOptional.isPresent()) {
            peliculaRepository.deleteById(requestDto.getId());
            return true;
        } else {
            return false;
        }
    }

    @Override
    public List<PeliculaSalaResponseDto> buscarPeliculaPorNombreYSala(BuscarPeliculaPorNombreYSalaRequestDto requestDto) {
        List<Object[]> resultados = peliculaRepository.buscarPeliculaPorNombreYSalaNativo(
                requestDto.getNombrePelicula(),
                requestDto.getIdSala()
        );
        return resultados.stream().map(row -> new PeliculaSalaResponseDto(
                (Integer) row[0],
                (String) row[1],
                (Integer) row[2],
                ((Date) row[3]).toLocalDate(),
                (Integer) row[4],
                (String) row[5]
        )).collect(Collectors.toList());
    }

    @Override
    public PeliculaSalacine asignarPeliculaASala(AsignarPeliculaSalaRequestDto requestDto) {
        Pelicula pelicula = peliculaRepository.findById(requestDto.getPeliculaId())
                .orElseThrow(() -> new RuntimeException("No se encontró la película con el ID: " + requestDto.getPeliculaId()));
        SalaCine salaCine = salaCineRepository.findById(requestDto.getSalaCineId())
                .orElseThrow(() -> new RuntimeException("No se encontró la sala de cine con el ID: " + requestDto.getSalaCineId()));
        PeliculaSalacineId peliculaSalacineId = new PeliculaSalacineId();
        peliculaSalacineId.setPeliculaId(pelicula.getId());
        peliculaSalacineId.setSalaCineId(salaCine.getId());
        PeliculaSalacine asignacion = new PeliculaSalacine();
        asignacion.setId(peliculaSalacineId);
        asignacion.setPelicula(pelicula);
        asignacion.setSalaCine(salaCine);
        asignacion.setFechaPublicacion(requestDto.getFechaPublicacion());
        asignacion.setFechaFin(requestDto.getFechaFin());
        return peliculaSalacineRepository.save(asignacion);
    }

    @Override
    public PeliculasPorFechaResponseDto buscarPorFechaPublicacion(BuscarPorFechaRequestDto requestDto) {
        List<PeliculaSalacine> asignaciones = peliculaSalacineRepository.findByFechaPublicacion(requestDto.getFechaPublicacion());

        List<Pelicula> peliculas = asignaciones.stream()
                .map(PeliculaSalacine::getPelicula)
                .distinct()
                .collect(Collectors.toList());

        // --- INICIO DE CORRECCIÓN ---
        PeliculasPorFechaResponseDto response = new PeliculasPorFechaResponseDto();
        response.setCantidad(peliculas.size());
        response.setPeliculas(peliculas);
        return response;
        // --- FIN DE CORRECCIÓN ---
    }
}