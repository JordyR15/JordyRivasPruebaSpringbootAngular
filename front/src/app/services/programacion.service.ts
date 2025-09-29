import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

// Interfaz para la data de asignación
export interface Asignacion {
  peliculaId: number;
  salaCineId: number; // <-- CAMBIO REALIZADO AQUÍ
  fechaPublicacion: string;
  fechaFin: string;
}

@Injectable({
  providedIn: 'root'
})
export class ProgramacionService {

  private apiUrl = 'http://localhost:8080/api/peliculas/asignar-sala';

  constructor(private http: HttpClient) { }

  /**
   * Asigna una película a una sala con fechas de proyección.
   * @param asignacionData Objeto con los IDs y las fechas.
   */
  asignarPeliculaASala(asignacionData: Asignacion): Observable<any> {
    return this.http.post(this.apiUrl, asignacionData);
  }
}
