package org.example.pruebaspring.repository;

import org.example.pruebaspring.entity.PeliculaSalacine;
import org.example.pruebaspring.entity.PeliculaSalacineId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface PeliculaSalacineRepository extends JpaRepository<PeliculaSalacine, PeliculaSalacineId> {

    // Busca todas las asignaciones que coincidan con una fecha de publicación específica
    List<PeliculaSalacine> findByFechaPublicacion(LocalDate fechaPublicacion);
}