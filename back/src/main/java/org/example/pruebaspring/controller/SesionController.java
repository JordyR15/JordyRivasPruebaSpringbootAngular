package org.example.pruebaspring.controller;

import org.example.pruebaspring.dto.ForgotPasswordRequestDto;
import org.example.pruebaspring.dto.LoginRequestDto;
import org.example.pruebaspring.dto.LoginResponseDto;
import org.example.pruebaspring.dto.ResetPasswordRequestDto;
import org.example.pruebaspring.services.ISesionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.HashMap; // <-- 1. AÑADE ESTE IMPORT
import java.util.Map;     // <-- 2. AÑADE ESTE IMPORT

@RestController
@RequestMapping("/api/auth")
public class SesionController {

    @Autowired
    private ISesionService sesionService;

    // La dependencia a RolServicesImpl no se usaba y ha sido eliminada.

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequestDto loginRequestDto) {
        try {
            LoginResponseDto response = sesionService.login(loginRequestDto);
            return ResponseEntity.ok(response);
        } catch (AuthenticationException e) {
            // Es mejor capturar la excepción específica de autenticación para dar una respuesta más precisa.
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Credenciales inválidas.");
        } catch (RuntimeException e) {
            // Captura para otros posibles errores inesperados.
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    // El endpoint de logout ha sido eliminado.
    // La invalidación del token JWT es responsabilidad del cliente (borrar el token).

    // Endpoint para restablecer la contraseña
    // --- INICIO DE CAMBIOS ---
    @PostMapping("/forgot-password")
    public ResponseEntity<Map<String, String>> forgotPassword(@Valid @RequestBody ForgotPasswordRequestDto requestDto) { // <-- 3. CAMBIA EL TIPO DE RETORNO
        sesionService.forgotPassword(requestDto);

        // 4. CREA UN MAPA PARA LA RESPUESTA JSON
        Map<String, String> response = new HashMap<>();
        response.put("message", "Si su dirección de correo electrónico está en nuestra base de datos, recibirá un enlace para restablecer la contraseña.");

        return ResponseEntity.ok(response); // <-- 5. DEVUELVE EL OBJETO JSON
    }

    @PostMapping("/reset-password")
    public ResponseEntity<String> resetPassword(@Valid @RequestBody ResetPasswordRequestDto requestDto) {
        try {
            sesionService.resetPassword(requestDto);
            return ResponseEntity.ok("Su contraseña ha sido actualizada exitosamente.");
        } catch (RuntimeException e) {
            // Captura errores como "token expirado" o "token inválido"
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

}