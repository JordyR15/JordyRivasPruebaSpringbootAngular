import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Pelicula } from './peliculas.service';

export interface Sala {
  id?: number;
  nombre: string;
}

@Injectable({
  providedIn: 'root'
})
export class SalasService {

  private apiUrl = 'http://localhost:8080/api/salas';

  constructor(private http: HttpClient) { }

  addSala(salaData: { nombre: string }): Observable<Sala> {
    return this.http.post<Sala>(this.apiUrl, salaData);
  }

  getTodasSalas(): Observable<Sala[]> {
    return this.http.post<Sala[]>(`${this.apiUrl}/list`, {});
  }


  getPeliculasPorSala(salaId: number): Observable<Pelicula[]> {
    const body = { id: salaId };
    return this.http.post<Pelicula[]>(`${this.apiUrl}/peliculas-asignadas`, body);
  }
}
