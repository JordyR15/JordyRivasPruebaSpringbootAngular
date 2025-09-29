package org.example.pruebaspring.services.impl;

import org.example.pruebaspring.entity.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.example.pruebaspring.repository.RolRepository;
import org.springframework.stereotype.Service;

@Service

public class RolServicesImpl {
    @Autowired
    private RolRepository rolRepository;
    public Role saveRole(Role rolEntity) { return rolRepository.save(rolEntity); }

}
