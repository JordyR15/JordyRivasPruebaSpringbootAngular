import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators'; // Importante para la transformación

// ... (Interfaz Pelicula y ResultadoBusqueda sin cambios)
export interface Pelicula {
  id?: number;
  nombre: string;
  duracion: number;
}
export interface ResultadoBusqueda {
  nombre_pelicula: string;
  duracion: number;
  fecha_publicacion: string;
  nombre_sala: string;
}

@Injectable({
  providedIn: 'root'
})
export class PeliculasService {

  private apiUrl = 'http://localhost:8080/api/peliculas';

  constructor(private http: HttpClient) { }

  // ... (resto de métodos sin cambios)
  addPelicula(peliculaData: { nombre: string, duracion: number }): Observable<any> {
    return this.http.post(this.apiUrl, peliculaData);
  }
  getTodasPeliculas(): Observable<Pelicula[]> {
    return this.http.post<Pelicula[]>(`${this.apiUrl}/list`, {});
  }
  getPeliculaById(id: number): Observable<Pelicula> {
    return this.http.post<Pelicula>(`${this.apiUrl}/detail`, { id: id });
  }
  updatePelicula(peliculaData: Pelicula): Observable<any> {
    return this.http.post(`${this.apiUrl}/update`, peliculaData);
  }
  deletePelicula(peliculaId: number): Observable<any> {
    return this.http.post(`${this.apiUrl}/delete`, { id: peliculaId });
  }
  buscarPeliculas(nombre: string, salaId: number | null): Observable<ResultadoBusqueda[]> {
    const body = {
      nombrePelicula: nombre,
      idSala: salaId
    };
    return this.http.post<any[]>(`${this.apiUrl}/buscar-por-nombre-y-sala`, body).pipe(
      map(respuestaDelBackend =>
        respuestaDelBackend.map(item => ({
          nombre_pelicula: item.nombre_pelicula,
          duracion: item.duracion,
          fecha_publicacion: item.fecha_publicacion,
          nombre_sala: item.nombre_sala
        } as ResultadoBusqueda))
      )
    );
  }

  // --- MÉTODO CORREGIDO AQUÍ ---
  /**
   * Busca películas por fecha y extrae el array de la respuesta.
   */
  buscarPorFecha(fecha: string): Observable<Pelicula[]> {
    const body = { fechaPublicacion: fecha };

    // 1. La petición ahora espera un objeto 'any' porque la respuesta es compleja.
    return this.http.post<any>(`${this.apiUrl}/buscar-por-fecha`, body).pipe(
      // 2. Usamos map para transformar la respuesta completa del backend.
      map(respuestaCompleta => {
        // 3. Devolvemos SOLAMENTE el array que está dentro de la propiedad 'peliculas'.
        return respuestaCompleta.peliculas;
      })
    );
  }
}
