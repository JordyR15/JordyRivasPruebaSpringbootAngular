package org.example.pruebaspring.services.impl;

import org.example.pruebaspring.services.IEmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailServiceImpl implements IEmailService {

    // Spring inyecta automáticamente el sender configurado en application.properties
    @Autowired
    private JavaMailSender mailSender;

    // Inyectamos el email del remitente para no tener que escribirlo a mano
    @Value("${spring.mail.username}")
    private String fromEmail;

    @Override
    public void sendPasswordResetEmail(String to, String token) {
        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setFrom(fromEmail);
            message.setTo(to);
            message.setSubject("Solicitud de Restablecimiento de Contraseña");

            // IMPORTANTE: Esta URL debe apuntar a la página de tu frontend
            // que se encarga de pedir la nueva contraseña.
            String resetUrl = "http://localhost:3000/reset-password?token=" + token;

            String emailBody = String.format(
                    "Hola,\n\n" +
                            "Has solicitado restablecer tu contraseña. Haz clic en el siguiente enlace para continuar:\n" +
                            "%s\n\n" +
                            "Si no solicitaste esto, por favor ignora este correo.\n\n" +
                            "Gracias.",
                    resetUrl
            );

            message.setText(emailBody);

            // Envía el correo
            mailSender.send(message);

        } catch (Exception e) {
            // En una aplicación real, es crucial loguear este error.
            // Por ahora, lo imprimimos en la consola de errores.
            System.err.println("Error al enviar el correo de restablecimiento: " + e.getMessage());
            // Podrías lanzar una excepción personalizada si prefieres manejar el error más arriba.
            // throw new RuntimeException("Error al enviar el correo.");
        }
    }
}
    