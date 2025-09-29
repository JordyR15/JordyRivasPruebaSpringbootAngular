package org.example.pruebaspring.controller;

import org.example.pruebaspring.dto.CreateUserRequestDto;
import org.example.pruebaspring.dto.ListUserRequestDto;
import org.example.pruebaspring.dto.UserListResponseDto;
import org.example.pruebaspring.services.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.HashMap; // <-- 1. AÑADE ESTE IMPORT
import java.util.List;
import java.util.Map;     // <-- 2. AÑADE ESTE IMPORT

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private IUserService userService;

    // --- INICIO DE CAMBIOS ---
    @PostMapping
    public ResponseEntity<Map<String, String>> createUser(@Valid @RequestBody CreateUserRequestDto createUserRequestDto) { // <-- 3. CAMBIA EL TIPO DE RETORNO
        try {
            userService.createUser(createUserRequestDto);

            // 4. CREA UN MAPA PARA LA RESPUESTA JSON
            Map<String, String> response = new HashMap<>();
            response.put("message", "Usuario creado exitosamente.");

            return ResponseEntity.status(HttpStatus.CREATED).body(response); // <-- 5. DEVUELVE EL OBJETO JSON

        } catch (RuntimeException e) {
            // Para los errores, también es buena práctica devolver JSON
            Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("error", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
        }
    }
    // --- FIN DE CAMBIOS ---

    @PostMapping("/list")
    public ResponseEntity<List<UserListResponseDto>> listAllUsers(@RequestBody(required = false) ListUserRequestDto requestDto) {
        List<UserListResponseDto> users = userService.listUsers(requestDto);
        return ResponseEntity.ok(users);
    }
}