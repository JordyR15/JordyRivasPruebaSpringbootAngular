import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { PeliculasService, Pelicula } from '../../services/peliculas.service';

@Component({
  selector: 'app-reporte-peliculas-fecha',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './reporte-peliculas-fecha.component.html',
})
export class ReportePeliculasFechaComponent {

  // Propiedad para vincular al input de fecha
  fechaBusqueda: string = '';
  // Array para guardar los resultados
  peliculasEncontradas: Pelicula[] = [];
  // Flag para controlar la UI
  busquedaRealizada: boolean = false;
  errorMessage: string | null = null;

  constructor(private peliculasService: PeliculasService) { }

  onBuscar(): void {
    this.errorMessage = null;
    this.busquedaRealizada = true;

    if (!this.fechaBusqueda) {
      this.errorMessage = 'Por favor, seleccione una fecha.';
      this.peliculasEncontradas = [];
      return;
    }

    this.peliculasService.buscarPorFecha(this.fechaBusqueda).subscribe({
      next: (response) => {
        this.peliculasEncontradas = response;
      },
      error: (err: any) => {
        console.error('Error al buscar películas por fecha', err);
        this.errorMessage = err.error?.message || 'Ocurrió un error al generar el reporte.';
        this.peliculasEncontradas = [];
      }
    });
  }
}
