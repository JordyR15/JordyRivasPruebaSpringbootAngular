import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { PeliculasService, Pelicula } from '../../services/peliculas.service';

@Component({
  selector: 'app-edit-pelicula',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './edit-pelicula.component.html',
  styleUrls: ['./edit-pelicula.component.css']
})
export class EditPeliculaComponent implements OnInit {

  pelicula: Pelicula = {
    nombre: '',
    duracion: 0
  };
  errorMessage: string | null = null;
  peliculaId: number | null = null;

  constructor(
    private peliculasService: PeliculasService,
    private route: ActivatedRoute,
    public router: Router // <-- CAMBIO: 'private' a 'public'
  ) { }

  ngOnInit(): void {
    this.route.paramMap.subscribe(params => {
      const idParam = params.get('id');
      if (idParam) {
        this.peliculaId = +idParam;
        this.loadPelicula(this.peliculaId);
      } else {
        this.errorMessage = 'No se proporcionó un ID de película.';
        this.router.navigate(['/peliculas/listar']);
      }
    });
  }

  loadPelicula(id: number): void {
    this.peliculasService.getPeliculaById(id).subscribe({
      next: (data) => {
        this.pelicula = data;
      },
      error: (err) => {
        this.errorMessage = err.error?.message || 'No se pudo cargar la película.';
        this.router.navigate(['/peliculas/listar']);
      }
    });
  }

  onSubmit(): void {
    this.errorMessage = null;
    if (!this.pelicula.nombre || this.pelicula.duracion <= 0) {
      this.errorMessage = 'Por favor, complete todos los campos con valores válidos.';
      return;
    }

    if (this.peliculaId) {
      this.pelicula.id = this.peliculaId;
      this.peliculasService.updatePelicula(this.pelicula).subscribe({
        next: () => {
          alert('¡La película ha sido actualizada correctamente!');
          this.router.navigate(['/peliculas/listar']);
        },
        error: (err) => {
          this.errorMessage = err.error?.message || 'Ocurrió un error al actualizar la película.';
        }
      });
    }
  }
}
