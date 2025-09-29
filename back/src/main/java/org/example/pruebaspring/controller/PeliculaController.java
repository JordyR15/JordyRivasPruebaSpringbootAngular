package org.example.pruebaspring.controller;

import org.example.pruebaspring.dto.*;
import org.example.pruebaspring.entity.Pelicula;
import org.example.pruebaspring.entity.PeliculaSalacine;
import org.example.pruebaspring.services.IPeliculaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/peliculas")
public class PeliculaController {

    @Autowired
    private IPeliculaService peliculaService;

    @PostMapping
    public ResponseEntity<Map<String, Object>> createPelicula(@Valid @RequestBody CreatePeliculaRequestDto requestDto) {
        Pelicula nuevaPelicula = peliculaService.createPelicula(requestDto);

        Map<String, Object> response = new HashMap<>();
        response.put("message", "Película creada exitosamente.");
        response.put("pelicula", nuevaPelicula);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PostMapping("/list")
    public ResponseEntity<List<Pelicula>> getAllPeliculas() {
        List<Pelicula> peliculas = peliculaService.getAllPeliculas();
        return ResponseEntity.ok(peliculas);
    }

    @PostMapping("/update")
    public ResponseEntity<Map<String, Object>> updatePelicula(@Valid @RequestBody UpdatePeliculaRequestDto requestDto) {
        Optional<Pelicula> peliculaActualizada = peliculaService.updatePelicula(requestDto);
        Map<String, Object> response = new HashMap<>();

        if (peliculaActualizada.isPresent()) {
            response.put("message", "Película actualizada exitosamente.");
            response.put("pelicula", peliculaActualizada.get());
            return ResponseEntity.ok(response);
        } else {
            response.put("error", "No se encontró la película con el ID: " + requestDto.getId());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
    }

    @PostMapping("/delete")
    public ResponseEntity<Map<String, String>> deletePelicula(@Valid @RequestBody DeletePeliculaRequestDto requestDto) {
        boolean eliminado = peliculaService.deletePelicula(requestDto);
        Map<String, String> response = new HashMap<>();

        if (eliminado) {
            response.put("message", "Película eliminada exitosamente.");
            return ResponseEntity.ok(response);
        } else {
            response.put("error", "No se encontró la película con el ID: " + requestDto.getId());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
    }

    @PostMapping("/buscar-por-nombre-y-sala")
    public ResponseEntity<List<PeliculaSalaResponseDto>> buscarPeliculaPorNombreYSala(
            @Valid @RequestBody BuscarPeliculaPorNombreYSalaRequestDto requestDto) {
        List<PeliculaSalaResponseDto> resultados = peliculaService.buscarPeliculaPorNombreYSala(requestDto);
        if (resultados.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build(); // O un mensaje de "no encontrado"
        }
        return ResponseEntity.ok(resultados);
    }

    @PostMapping("/asignar-sala")
    public ResponseEntity<?> asignarPeliculaASala(@Valid @RequestBody AsignarPeliculaSalaRequestDto requestDto) {
        try {
            PeliculaSalacine nuevaAsignacion = peliculaService.asignarPeliculaASala(requestDto);
            Map<String, Object> response = new HashMap<>();
            response.put("message", "Película asignada a la sala exitosamente.");
            response.put("asignacion", nuevaAsignacion);
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (RuntimeException e) {
            Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("error", e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
        }
    }

    @PostMapping("/buscar-por-fecha")
    public ResponseEntity<PeliculasPorFechaResponseDto> buscarPorFecha(
            @Valid @RequestBody BuscarPorFechaRequestDto requestDto) {
        PeliculasPorFechaResponseDto response = peliculaService.buscarPorFechaPublicacion(requestDto);
        return ResponseEntity.ok(response);
    }

}