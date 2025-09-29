package org.example.pruebaspring.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference; // <-- AÑADE ESTE IMPORT
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "sala_cine")
@Getter
@Setter
public class SalaCine {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_sala")
    private Integer id;

    @Column(name = "nombre", nullable = false)
    private String nombre;

    @Column(name = "estado")
    private Integer estado;

    @OneToMany(mappedBy = "salaCine")
    @JsonManagedReference("sala-ref") // <-- AÑADE ESTA ANOTACIÓN
    private Set<PeliculaSalacine> peliculaSalacines;
}
