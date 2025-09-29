package org.example.pruebaspring.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference; // <-- AÑADE ESTE IMPORT
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "pelicula")
@Getter
@Setter
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Pelicula {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_pelicula")
    private Integer id;

    @Column(name = "nombre", nullable = false)
    private String nombre;

    @Column(name = "duracion")
    private Integer duracion;

    @OneToMany(mappedBy = "pelicula")
    @JsonManagedReference("pelicula-ref") // <-- AÑADE ESTA ANOTACIÓN
    private Set<PeliculaSalacine> peliculaSalacines;
}