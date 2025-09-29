import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { RouterModule, Router } from '@angular/router';
import { AuthService } from '../../services/auth.service';

@Component({
  selector: 'app-login',
  standalone: true,
  imports: [CommonModule, FormsModule, RouterModule],
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent {

  credentials = {
    username: '',
    password: ''
  };

  errorMessage: string | null = null;

  constructor(
    private authService: AuthService,
    private router: Router
  ) {}

  onSubmit() {
    this.errorMessage = null;
    console.log('Enviando credenciales para login:', this.credentials);

    this.authService.login(this.credentials).subscribe({
      next: (response) => {
        console.log('¡Login exitoso!', response);

        // 1. Guardar el token
        localStorage.setItem('token', response.token);

        // === ¡CAMBIO CRUCIAL AQUÍ! ===
        // Redirigimos a la nueva ruta por defecto '/peliculas/listar'
        this.router.navigate(['/peliculas/listar']);
      },
      error: (err) => {
        console.error('Error en el login:', err);
        this.errorMessage = 'Usuario o contraseña incorrectos. Por favor, inténtalo de nuevo.';
      }
    });
  }
}
