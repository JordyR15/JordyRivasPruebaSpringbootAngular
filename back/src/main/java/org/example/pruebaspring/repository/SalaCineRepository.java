package org.example.pruebaspring.repository;

import org.example.pruebaspring.entity.SalaCine;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional; // <-- AÑADE ESTE IMPORT

@Repository
public interface SalaCineRepository extends JpaRepository<SalaCine, Integer> {

    // --- INICIO DE CAMBIOS ---
    // Busca una sala de cine por su nombre, ignorando mayúsculas/minúsculas
    Optional<SalaCine> findByNombreIgnoreCase(String nombre);
    // --- FIN DE CAMBIOS ---
}