package org.example.pruebaspring.entity;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.time.OffsetDateTime;

@Getter
@Setter
@Entity
@Table(name = "persons")
public class Person {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "first_name", nullable = false)
    @Type(type = "org.hibernate.type.TextType")
    private String firstName;

    @Column(name = "last_name", nullable = false)
    @Type(type = "org.hibernate.type.TextType")
    private String lastName;

    @Column(name = "email", nullable = false)
    @Type(type = "org.hibernate.type.TextType")
    private String email;

    @Column(name = "created_at")
    private OffsetDateTime createdAt;

}