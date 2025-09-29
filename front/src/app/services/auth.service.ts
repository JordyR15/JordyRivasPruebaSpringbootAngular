import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class AuthService {

  // La URL base de tu API.
  private apiUrl = 'http://localhost:8080/api';

  constructor(private http: HttpClient) { }

  /**
   * Envía las credenciales al backend para iniciar sesión.
   * Endpoint: POST /api/auth/login
   */
  login(credentials: any): Observable<any> {
    // CORREGIDO: Apunta a la nueva URL de login
    return this.http.post(`${this.apiUrl}/auth/login`, credentials);
  }

  /**
   * Envía una solicitud para el cambio de contraseña.
   * Endpoint: POST /api/auth/forgot-password
   */
  requestPasswordChange(email: string): Observable<any> {
    // CORREGIDO: Apunta a la nueva URL de forgot-password
    // Asumo que el backend espera un objeto con el email.
    return this.http.post(`${this.apiUrl}/auth/forgot-password`, { email: email });
  }

  // NOTA: No tienes un endpoint de logout en la lista, pero si lo tuvieras, iría aquí.
  // --- NUEVO MÉTODO DE REGISTRO ---
  /**
   * Envía los datos de un nuevo usuario para registrarlo.
   * Endpoint: POST /api/users
   */
  register(userData: any): Observable<any> {
    return this.http.post(`${this.apiUrl}/users`, userData);
  }
}
