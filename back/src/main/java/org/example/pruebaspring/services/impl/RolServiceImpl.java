package org.example.pruebaspring.services.impl;

import org.example.pruebaspring.dto.RoleListRequestDto;
import org.example.pruebaspring.dto.RoleResponseDto;
import org.example.pruebaspring.repository.RolRepository;
import org.example.pruebaspring.services.IRolService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class RolServiceImpl implements IRolService {

    @Autowired
    private RolRepository rolRepository;

    @Override
    public List<RoleResponseDto> getAllRoles(RoleListRequestDto requestDto) {
        // Por ahora, se ignora el contenido del requestDto.
        // Se buscan todos los roles en la base de datos.
        return rolRepository.findAll()
                .stream()
                // Cada entidad 'Role' se convierte en un 'RoleResponseDto'.
                .map(role -> new RoleResponseDto(role.getId(), role.getName()))
                .collect(Collectors.toList());
    }
}

    