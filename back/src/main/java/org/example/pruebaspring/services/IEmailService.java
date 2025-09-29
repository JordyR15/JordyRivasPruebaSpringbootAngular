package org.example.pruebaspring.services;

public interface IEmailService {
    /**
     * Envía un correo electrónico para restablecer la contraseña.
     * @param to La dirección de correo del destinatario.
     * @param token El token de restablecimiento único.
     */
    void sendPasswordResetEmail(String to, String token);
}
    