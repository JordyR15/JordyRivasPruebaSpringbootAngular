package org.example.pruebaspring.services;

import org.example.pruebaspring.dto.RoleListRequestDto;
import org.example.pruebaspring.dto.RoleResponseDto;
import java.util.List;

public interface IRolService {
    /**
     * Devuelve una lista de todos los roles del sistema.
     * @param requestDto DTO de la petición (actualmente no se usa).
     * @return Una lista de DTOs de roles.
     */
    List<RoleResponseDto> getAllRoles(RoleListRequestDto requestDto);
}