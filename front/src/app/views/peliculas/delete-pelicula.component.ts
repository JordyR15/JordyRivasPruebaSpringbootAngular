import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ActivatedRoute } from '@angular/router'; // Para obtener el ID de la URL
import { PeliculasService } from '../../services/peliculas.service'; // Importa el nuevo servicio

@Component({
  selector: 'app-delete-pelicula',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './delete-pelicula.component.html',
  styleUrls: ['./delete-pelicula.component.css']
})
export class DeletePeliculaComponent implements OnInit {
  peliculaId: number = 0;
  peliculaNombre: string = 'Película Desconocida'; // Para mostrar en la confirmación

  constructor(
    private route: ActivatedRoute,
    private peliculasService: PeliculasService
  ) { }

  ngOnInit(): void {
    // Obtener el ID de la URL
    this.route.paramMap.subscribe(params => {
      this.peliculaId = Number(params.get('id'));
      // Aquí normalmente cargarías el nombre de la película para la confirmación
      // this.peliculasService.getPeliculaById(this.peliculaId).subscribe(data => this.peliculaNombre = data.nombre);
      console.log('Preparando para eliminar película con ID:', this.peliculaId);
    });
  }

  confirmarEliminar() {
    this.peliculasService.deletePelicula(this.peliculaId).subscribe({
      next: (response) => {
        console.log('Película eliminada con éxito', response);
        alert('Película eliminada con éxito!');
        // Aquí podrías redirigir a la lista: this.router.navigate(['/peliculas/listar']);
      },
      error: (err) => {
        console.error('Error al eliminar película', err);
        alert('Error al eliminar película: ' + (err.error?.message || 'Verifica la consola.'));
      }
    });
  }
}
