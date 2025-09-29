package org.example.pruebaspring.repository;

import org.example.pruebaspring.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);
    @Query("SELECT u FROM User u JOIN FETCH u.userRoles ur JOIN FETCH ur.role JOIN FETCH u.person")
    List<User> findAllWithDetails();

    // Método para encontrar un usuario a través del email de su persona asociada
    Optional<User> findByPersonEmail(String email);

    // Método para encontrar un usuario por su token de reseteo
    Optional<User> findByResetToken(String resetToken);
    
}