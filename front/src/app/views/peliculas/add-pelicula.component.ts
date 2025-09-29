import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { Router } from '@angular/router';
import { PeliculasService } from '../../services/peliculas.service'; // Importamos el servicio

@Component({
  selector: 'app-add-pelicula',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './add-pelicula.component.html',
  styleUrls: ['./add-pelicula.component.css']
})
export class AddPeliculaComponent {

  // Objeto para vincular con los campos del formulario
  pelicula = {
    nombre: '',
    duracion: 0 // Cambiado de null a 0 para asegurar que siempre sea un número
  };

  errorMessage: string | null = null;

  constructor(
    private peliculasService: PeliculasService,
    private router: Router // Inyectamos el Router para poder navegar
  ) { }

  onSubmit() {
    this.errorMessage = null; // Limpiamos errores previos

    // Validación simple para asegurar que los campos no estén vacíos
    // Ahora, si el usuario no introduce nada, duracion será 0 y pasará esta validación.
    // Si necesitas que el usuario *siempre* introduzca un valor > 0, deberías ajustar la validación.
    if (!this.pelicula.nombre || this.pelicula.duracion === null || this.pelicula.duracion === undefined) {
      this.errorMessage = 'Por favor, complete todos los campos.';
      return;
    }

    console.log('Enviando nueva película:', this.pelicula);

    // Llamamos al método del servicio para agregar la película
    this.peliculasService.addPelicula(this.pelicula).subscribe({
      // Esto se ejecuta si el backend responde con éxito (2xx)
      next: (response) => {
        console.log('Película agregada con éxito', response);
        alert('¡La película ha sido agregada correctamente!');

        // Opcional: Redirigir al usuario a la lista de películas
        this.router.navigate(['/peliculas/listar']);
      },
      // Esto se ejecuta si el backend responde con un error (4xx o 5xx)
      error: (err) => {
        console.error('Error al agregar película', err);
        this.errorMessage = err.error?.message || 'Ocurrió un error al guardar la película.';
      }
    });
  }
}
