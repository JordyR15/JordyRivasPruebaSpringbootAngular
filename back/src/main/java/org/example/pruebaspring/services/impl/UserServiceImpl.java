package org.example.pruebaspring.services.impl;

import org.example.pruebaspring.dto.CreateUserRequestDto;
import org.example.pruebaspring.dto.ListUserRequestDto;
import org.example.pruebaspring.dto.UserListResponseDto;
import org.example.pruebaspring.dto.UserResponseDto;
import org.example.pruebaspring.entity.*;
import org.example.pruebaspring.repository.*;
import org.example.pruebaspring.services.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.OffsetDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements IUserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PersonRepository personRepository;

    @Autowired
    private RolRepository rolRepository;

    @Autowired
    private UserRoleRepository userRoleRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    @Transactional // Asegura que toda la operación sea atómica (o todo se guarda o nada)
    public void createUser(CreateUserRequestDto requestDto) {
        // Buena práctica: verificar si el usuario ya existe
        if (userRepository.findByUsername(requestDto.getUsername()).isPresent()) {
            throw new RuntimeException("El nombre de usuario ya está en uso: " + requestDto.getUsername());
        }

        // 1. Crear y guardar la Persona
        Person person = new Person();
        person.setFirstName(requestDto.getFirstName());
        person.setLastName(requestDto.getLastName());
        person.setEmail(requestDto.getEmail());
        person.setCreatedAt(OffsetDateTime.now());
        Person savedPerson = personRepository.save(person);

        // 2. Crear y guardar el Usuario
        User user = new User();
        user.setUsername(requestDto.getUsername());
        user.setPasswordHash(passwordEncoder.encode(requestDto.getPassword()));
        user.setPerson(savedPerson);
        user.setCreatedAt(OffsetDateTime.now());
        User savedUser = userRepository.save(user);

        // 3. Encontrar el Rol
        Role role = rolRepository.findById(requestDto.getRoleId())
                .orElseThrow(() -> new RuntimeException("Rol no encontrado con ID: " + requestDto.getRoleId()));

        // 4. Vincular Usuario y Rol
        UserRole userRole = new UserRole();
        UserRoleId userRoleId = new UserRoleId();
        userRoleId.setUserId(savedUser.getId());
        userRoleId.setRoleId(role.getId());
        userRole.setId(userRoleId);
        userRole.setUser(savedUser);
        userRole.setRole(role);

        userRoleRepository.save(userRole);
    }

    @Override
    public List<UserListResponseDto> listUsers(ListUserRequestDto requestDto) {
        return userRepository.findAll()
                .stream()
                .map(user -> {
                    String email = null;
                    String fullName = null;
                    // 1. Comprobación de seguridad para la entidad Person
                    if (user.getPerson() != null) {
                        email = user.getPerson().getEmail();
                        // 2. Uso de los métodos correctos: getFirstName() y getLastName()
                        fullName = user.getPerson().getFirstName() + " " + user.getPerson().getLastName();
                    }

                    List<String> roleNames = Collections.emptyList(); // 3. Valor por defecto seguro
                    // 4. Comprobación de seguridad para la colección de roles
                    if (user.getUserRoles() != null && !user.getUserRoles().isEmpty()) {
                        roleNames = user.getUserRoles().stream()
                                // 5. Comprobación de seguridad para cada rol individual
                                .map(userRole -> userRole.getRole() != null ? userRole.getRole().getName() : null)
                                // 6. Se filtran resultados nulos para una lista limpia
                                .filter(Objects::nonNull)
                                .collect(Collectors.toList());
                    }

                    // Se construye el DTO de respuesta con las variables seguras
                    return new UserListResponseDto(
                            user.getId().intValue(),
                            user.getUsername(),
                            email,
                            fullName,
                            roleNames
                    );
                })
                .collect(Collectors.toList());
    }

    private UserResponseDto mapToUserResponseDto(User user) {
        // Ahora los roles ya vienen cargados gracias a la consulta optimizada.
        List<String> roles = user.getUserRoles().stream()
                .map(userRole -> userRole.getRole().getName())
                .collect(Collectors.toList());

        return UserResponseDto.builder()
                .id(user.getId())
                .username(user.getUsername())
                .firstName(user.getPerson().getFirstName())
                .lastName(user.getPerson().getLastName())
                .email(user.getPerson().getEmail())
                .createdAt(user.getCreatedAt())
                .roles(roles)
                .build();
    }
}