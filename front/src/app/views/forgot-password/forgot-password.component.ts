import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { RouterModule } from '@angular/router';
// HttpClientModule ya no es necesario aquí
import { AuthService } from '../../services/auth.service';

@Component({
  selector: 'app-forgot-password',
  standalone: true,
  imports: [
    CommonModule,
    FormsModule,
    RouterModule
    // HttpClientModule ya no es necesario aquí
  ],
  templateUrl: './forgot-password.component.html',
  styleUrls: ['./forgot-password.component.css']
})
export class ForgotPasswordComponent {
  email: string = '';

  constructor(private authService: AuthService) {}

  onSubmit() {
    this.authService.requestPasswordChange(this.email).subscribe({
      next: (response) => {
        console.log('Solicitud enviada con éxito', response);
        alert('Si tu correo está en nuestros registros, recibirás un email con instrucciones.');
      },
      error: (err) => {
        console.error('Error al solicitar el cambio:', err);
        alert('No se pudo procesar la solicitud en este momento.');
      }
    });
  }
}
