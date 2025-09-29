package org.example.pruebaspring.repository;

import org.example.pruebaspring.entity.Session;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SessionRepository extends JpaRepository<Session, Long> {
    Optional<Session> findByIdSession(String idSession);
}