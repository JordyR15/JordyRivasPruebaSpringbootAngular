package org.example.pruebaspring.repository;

import org.example.pruebaspring.entity.Role;
import org.example.pruebaspring.entity.UserRole;
import org.example.pruebaspring.entity.UserRoleId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface UserRoleRepository extends JpaRepository<UserRole, UserRoleId> {

    @Query("select ur.role from UserRole ur where ur.user.id = :userId")
    List<Role> findRolesByUserId(@Param("userId") Long userId);

}
