import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { RouterModule, Router } from '@angular/router';
import { AuthService } from '../../services/auth.service';

@Component({
  selector: 'app-register',
  standalone: true,
  imports: [CommonModule, FormsModule, RouterModule],
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.css']
})
export class RegisterComponent {

  // Objeto para almacenar los datos del formulario
  registrationData = {
    username: '',
    password: '',
    confirmPassword: '',
    firstName: '',
    lastName: '',
    email: ''
  };

  errorMessage: string | null = null;

  constructor(
    private authService: AuthService,
    private router: Router
  ) {}

  onSubmit() {
    this.errorMessage = null;

    // 1. Validar que las contraseñas coincidan
    if (this.registrationData.password !== this.registrationData.confirmPassword) {
      this.errorMessage = 'Las contraseñas no coinciden.';
      return;
    }

    // 2. Preparamos el objeto a enviar, siguiendo la estructura del backend
    const userData = {
      username: this.registrationData.username,
      password: this.registrationData.password,
      firstName: this.registrationData.firstName,
      lastName: this.registrationData.lastName,
      email: this.registrationData.email,
      roleId: 2 // <-- FIJAMOS EL roleId EN 2
    };

    console.log('Enviando datos de registro:', userData);

    // 3. Llamar al servicio
    this.authService.register(userData).subscribe({
      next: (response) => {
        console.log('¡Registro exitoso!', response);
        alert('¡Te has registrado con éxito! Ahora puedes iniciar sesión.');
        this.router.navigate(['/login']);
      },
      error: (err) => {
        console.error('Error en el registro:', err);
        this.errorMessage = err.error?.message || 'Ocurrió un error durante el registro. El usuario o email ya podría existir.';
      }
    });
  }
}
