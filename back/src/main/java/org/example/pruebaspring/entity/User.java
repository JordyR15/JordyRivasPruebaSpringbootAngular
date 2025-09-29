package org.example.pruebaspring.entity;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.time.OffsetDateTime;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Getter
@Setter
@Entity
@Table(name = "users", uniqueConstraints = {
        // Es una mejor práctica definir restricciones a nivel de tabla
        @UniqueConstraint(columnNames = "username")
})
public class User implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "username", nullable = false)
    private String username;

    @Column(name = "password_hash", nullable = false)
    private String passwordHash;

    @Column(name = "created_at", nullable = false, updatable = false)
    private OffsetDateTime createdAt;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "person_id", nullable = false)
    private Person person;

    // Relación bidireccional para acceder a los roles del usuario fácilmente.
    // FetchType.EAGER es útil aquí para que Spring Security pueda cargar los roles durante la autenticación.
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true)
    private Set<UserRole> userRoles = new HashSet<>();

    /**
     * Asigna la fecha de creación automáticamente antes de que la entidad se guarde por primera vez.
     */
    @PrePersist
    protected void onCreate() {
        if (createdAt == null) {
            createdAt = OffsetDateTime.now();
        }
    }

    //<editor-fold desc="Implementación de UserDetails">
    /**
     * Devuelve los roles/permisos del usuario.
     * Spring Security usa esta información para las comprobaciones de autorización.
     * @return Una colección de autoridades otorgadas al usuario.
     */
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.userRoles.stream()
                .map(userRole -> new SimpleGrantedAuthority("ROLE_" + userRole.getRole().getName().toUpperCase()))
                .collect(Collectors.toList());
    }

    /**
     * Devuelve la contraseña (hash) usada para autenticar al usuario.
     * @return El hash de la contraseña.
     */
    @Override
    public String getPassword() {
        return this.passwordHash;
    }

    /**
     * Indica si la cuenta del usuario ha expirado.
     */
    @Override
    public boolean isAccountNonExpired() {
        return true; // Puedes añadir lógica personalizada aquí
    }

    /**
     * Indica si el usuario está bloqueado o no.
     */
    @Override
    public boolean isAccountNonLocked() {
        return true; // Puedes añadir lógica personalizada aquí
    }

    /**
     * Indica si las credenciales del usuario (contraseña) han expirado.
     */
    @Override
    public boolean isCredentialsNonExpired() {
        return true; // Puedes añadir lógica personalizada aquí
    }

    /**
     * Indica si el usuario está habilitado o deshabilitado.
     */
    @Override
    public boolean isEnabled() {
        return true; // Puedes añadir lógica personalizada aquí
    }
    //</editor-fold>

    @Column(name = "reset_token")
    private String resetToken;

    @Column(name = "reset_token_expiry")
    private java.time.OffsetDateTime resetTokenExpiry;
}