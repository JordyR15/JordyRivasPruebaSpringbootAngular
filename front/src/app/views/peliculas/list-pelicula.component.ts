import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { Router, RouterLink } from '@angular/router';
import { FormsModule } from '@angular/forms'; // 1. IMPORTANTE: Añadir FormsModule
import { PeliculasService, Pelicula } from '../../services/peliculas.service';

@Component({
  selector: 'app-list-pelicula',
  standalone: true,
  imports: [CommonModule, RouterLink, FormsModule], // 2. Añadir FormsModule aquí
  templateUrl: './list-pelicula.component.html',
  styleUrls: ['./list-pelicula.component.css']
})
export class ListPeliculaComponent implements OnInit {
  peliculas: Pelicula[] = [];

  // 3. Propiedades para gestionar la edición en línea
  editingRowId: number | null = null; // Guarda el ID de la fila que se está editando
  peliculaEditada: Pelicula | null = null; // Guarda una copia de los datos de la película en edición

  constructor(private peliculasService: PeliculasService, private router: Router) { }

  ngOnInit(): void {
    this.getTodasPeliculas();
  }

  getTodasPeliculas(): void {
    this.peliculasService.getTodasPeliculas().subscribe({
      next: (response) => {
        this.peliculas = response;
      },
      error: (err: any) => {
        console.error('Error al obtener películas', err);
        alert('Error al cargar películas: ' + (err.error?.message || 'Verifica la consola.'));
      }
    });
  }

  // --- MÉTODOS NUEVOS PARA LA EDICIÓN EN LÍNEA ---

  // 4. Se activa al hacer clic en "Editar"
  activarModoEdicion(pelicula: Pelicula): void {
    this.editingRowId = pelicula.id!;
    // Creamos una copia de la película para no modificar la original directamente
    this.peliculaEditada = { ...pelicula };
  }

  // 5. Se activa al hacer clic en "Cancelar"
  cancelarEdicion(): void {
    this.editingRowId = null;
    this.peliculaEditada = null;
  }

  // 6. Se activa al hacer clic en "Guardar"
  guardarCambios(): void {
    if (!this.peliculaEditada) return;

    // Llama al servicio para actualizar la película en el backend
    this.peliculasService.updatePelicula(this.peliculaEditada).subscribe({
      next: () => {
        // Busca el índice de la película original en el array
        const index = this.peliculas.findIndex(p => p.id === this.editingRowId);
        if (index !== -1) {
          // Actualiza el array local con los nuevos datos
          this.peliculas[index] = this.peliculaEditada!;
        }
        alert('Película actualizada con éxito.');
        this.cancelarEdicion(); // Sale del modo edición
      },
      error: (err: any) => {
        console.error('Error al actualizar la película', err);
        alert('Error al guardar los cambios: ' + (err.error?.message || 'Verifica la consola.'));
      }
    });
  }

  // --- FIN DE MÉTODOS NUEVOS ---

  eliminarPelicula(id: number | undefined): void {
    if (id === undefined) return;
    if (confirm('¿Estás seguro de que deseas eliminar esta película?')) {
      this.peliculasService.deletePelicula(id).subscribe({
        next: () => {
          alert('Película eliminada con éxito.');
          this.getTodasPeliculas();
        },
        error: (err: any) => {
          console.error('Error al eliminar película', err);
          alert('Error al eliminar la película: ' + (err.error?.message || 'Verifica la consola.'));
        }
      });
    }
  }
}
