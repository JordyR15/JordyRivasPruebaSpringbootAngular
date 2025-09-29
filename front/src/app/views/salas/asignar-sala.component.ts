import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { Router } from '@angular/router';

import { PeliculasService, Pelicula } from '../../services/peliculas.service';
import { SalasService, Sala } from '../../services/salas.service';
import { ProgramacionService, Asignacion } from '../../services/programacion.service';

@Component({
  selector: 'app-asignar-sala',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './asignar-sala.component.html',
  styleUrls: ['./asignar-sala.component.css']
})
export class AsignarSalaComponent implements OnInit {

  peliculas: Pelicula[] = [];
  salas: Sala[] = [];

  // Objeto para vincular con el formulario
  asignacion: Asignacion = {
    peliculaId: 0,
    salaCineId: 0, // <-- CAMBIO REALIZADO AQUÍ
    fechaPublicacion: '',
    fechaFin: ''
  };

  errorMessage: string | null = null;

  constructor(
    private peliculasService: PeliculasService,
    private salasService: SalasService,
    private programacionService: ProgramacionService,
    public router: Router
  ) { }

  ngOnInit(): void {
    this.loadPeliculas();
    this.loadSalas();
  }

  loadPeliculas(): void {
    this.peliculasService.getTodasPeliculas().subscribe({
      next: (data) => this.peliculas = data,
      error: (err: any) => this.errorMessage = 'Error al cargar la lista de películas.'
    });
  }

  loadSalas(): void {
    this.salasService.getTodasSalas().subscribe({
      next: (data) => {
        this.salas = data;
      },
      error: (err: any) => {
        this.errorMessage = 'Error al cargar la lista de salas.';
      }
    });
  }

  onSubmit(): void {
    this.errorMessage = null;

    // Validación
    if (!this.asignacion.peliculaId || !this.asignacion.salaCineId || !this.asignacion.fechaPublicacion || !this.asignacion.fechaFin) { // <-- CAMBIO REALIZADO AQUÍ
      this.errorMessage = 'Por favor, complete todos los campos del formulario.';
      return;
    }

    this.programacionService.asignarPeliculaASala(this.asignacion).subscribe({
      next: () => {
        alert('¡Película asignada a la sala con éxito!');
        this.router.navigate(['/dashboard']);
      },
      error: (err: any) => {
        console.error('Error al asignar la sala', err);
        this.errorMessage = err.error?.message || 'Ocurrió un error al realizar la asignación.';
      }
    });
  }
}
