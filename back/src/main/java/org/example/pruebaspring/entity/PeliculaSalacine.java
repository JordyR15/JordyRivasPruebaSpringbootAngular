package org.example.pruebaspring.entity;

import com.fasterxml.jackson.annotation.JsonBackReference; // <-- AÑADE ESTE IMPORT
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "pelicula_salacine")
@Getter
@Setter
public class PeliculaSalacine {

    @EmbeddedId
    private PeliculaSalacineId id;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("peliculaId")
    @JoinColumn(name = "id_pelicula")
    @JsonBackReference("pelicula-ref") // <-- AÑADE ESTA ANOTACIÓN
    private Pelicula pelicula;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("salaCineId")
    @JoinColumn(name = "id_sala_cine")
    @JsonBackReference("sala-ref") // <-- AÑADE ESTA ANOTACIÓN
    private SalaCine salaCine;

    @Column(name = "fecha_publicacion")
    private LocalDate fechaPublicacion;

    @Column(name = "fecha_fin")
    private LocalDate fechaFin;

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_pelicula_sala", insertable = false, updatable = false)
    private Integer idPeliculaSala;
}