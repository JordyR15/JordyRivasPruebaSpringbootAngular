package org.example.pruebaspring.services.impl;

import org.example.pruebaspring.dto.ForgotPasswordRequestDto;
import org.example.pruebaspring.dto.LoginRequestDto;
import org.example.pruebaspring.dto.LoginResponseDto;
import org.example.pruebaspring.dto.ResetPasswordRequestDto;
import org.example.pruebaspring.entity.User;
import org.example.pruebaspring.repository.UserRepository;
import org.example.pruebaspring.services.IEmailService;
import org.example.pruebaspring.services.ISesionService;
import org.example.pruebaspring.util.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class SesionServiceImpl implements ISesionService {

    // --- Dependencias necesarias para el flujo JWT ---
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtService jwtService;
    @Autowired
    private PasswordEncoder passwordEncoder;

    // --- Las dependencias SessionRepository, PasswordEncoder y UserRoleRepository
    // --- ya no son necesarias aquí y han sido eliminadas.

    @Override
    public LoginResponseDto login(LoginRequestDto loginRequestDto) {
        // 1. Dejamos que Spring Security valide las credenciales.
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequestDto.getUsername(),
                        loginRequestDto.getPassword()
                )
        );

        // 2. Si la autenticación es exitosa, obtenemos el usuario.
        User user = userRepository.findByUsername(loginRequestDto.getUsername())
                .orElseThrow(() -> new IllegalStateException("Usuario no encontrado después de una autenticación exitosa."));

        // 3. Generamos el token JWT para este usuario.
        String jwtToken = jwtService.generateToken(user);

        // 4. Obtenemos los roles desde el objeto User (que implementa UserDetails).
        List<String> roles = user.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toList());

        // 5. Devolvemos el token JWT real.
        return new LoginResponseDto(jwtToken, user.getUsername(), roles);
    }

    @Override
    public void logout(String sessionToken) {
        // Con JWT stateless, el logout es responsabilidad del cliente.
        throw new UnsupportedOperationException("El logout se gestiona en el cliente para la autenticación JWT.");
    }

    @Override
    public void resetPassword(ResetPasswordRequestDto requestDto) {
        // Busca al usuario que tenga este token de reseteo
        User user = userRepository.findByResetToken(requestDto.getToken())
                .orElseThrow(() -> new RuntimeException("Token de reseteo inválido o no encontrado."));

        // Verifica que el token no haya expirado
        if (user.getResetTokenExpiry().isBefore(OffsetDateTime.now())) {
            // Invalida el token si ha expirado
            user.setResetToken(null);
            user.setResetTokenExpiry(null);
            userRepository.save(user);
            throw new RuntimeException("El token de reseteo ha expirado.");
        }

        // Hashea y actualiza la nueva contraseña
        user.setPasswordHash(passwordEncoder.encode(requestDto.getNewPassword()));

        // Invalida el token para que no pueda ser reutilizado
        user.setResetToken(null);
        user.setResetTokenExpiry(null);

        userRepository.save(user);

    }


    @Autowired
    private IEmailService emailService; // <-- 2. INYECTAR EL SERVICIO

    // ...

    @Override
    public void forgotPassword(ForgotPasswordRequestDto requestDto) {
        userRepository.findByPersonEmail(requestDto.getEmail())
                .ifPresent(user -> {
                    // Genera y guarda el token como antes
                    String token = UUID.randomUUID().toString();
                    user.setResetToken(token);
                    user.setResetTokenExpiry(OffsetDateTime.now().plusHours(1));
                    userRepository.save(user);

                    // --- 3. LLAMAR AL MÉTODO PARA ENVIAR EL CORREO ---
                    emailService.sendPasswordResetEmail(user.getPerson().getEmail(), token);
                });
    }

}

