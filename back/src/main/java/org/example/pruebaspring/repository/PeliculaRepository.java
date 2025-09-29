package org.example.pruebaspring.repository;

import org.example.pruebaspring.entity.Pelicula;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PeliculaRepository extends JpaRepository<Pelicula, Integer> {

    // Método para llamar al procedimiento almacenado
    @Query(value = "SELECT * FROM buscar_pelicula_por_nombre_y_sala(:nombreBuscado, :salaId)", nativeQuery = true)
    List<Object[]> buscarPeliculaPorNombreYSalaNativo(
            @Param("nombreBuscado") String nombreBuscado,
            @Param("salaId") Integer salaId
    );
}