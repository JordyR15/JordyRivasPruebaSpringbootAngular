package org.example.pruebaspring.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
@Getter
@Setter
public class PeliculaSalacineId implements Serializable {

    @Column(name = "id_pelicula")
    private Integer peliculaId;

    @Column(name = "id_sala_cine")
    private Integer salaCineId;

    // hashCode and equals are necessary for composite keys
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PeliculaSalacineId that = (PeliculaSalacineId) o;
        return Objects.equals(peliculaId, that.peliculaId) && Objects.equals(salaCineId, that.salaCineId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(peliculaId, salaCineId);
    }
}