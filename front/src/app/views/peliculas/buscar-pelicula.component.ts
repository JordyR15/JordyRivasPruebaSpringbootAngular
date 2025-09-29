import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { PeliculasService, ResultadoBusqueda } from '../../services/peliculas.service';
import { SalasService, Sala } from '../../services/salas.service';

@Component({
  selector: 'app-buscar-pelicula',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './buscar-pelicula.component.html',
})
export class BuscarPeliculaComponent implements OnInit {

  salas: Sala[] = [];
  criteriosBusqueda = {
    nombre: '',
    salaId: null as number | null
  };
  resultadosBusqueda: ResultadoBusqueda[] = [];
  busquedaRealizada = false; // Para saber si mostrar el mensaje de "no se encontraron resultados"

  constructor(
    private peliculasService: PeliculasService,
    private salasService: SalasService
  ) { }

  ngOnInit(): void {
    this.loadSalas();
  }

  loadSalas(): void {
    this.salasService.getTodasSalas().subscribe({
      next: (data) => this.salas = data,
      error: (err: any) => console.error('Error al cargar la lista de salas', err)
    });
  }

  onBuscar(): void {
    this.busquedaRealizada = true; // Marcamos que la búsqueda se ha intentado
    this.peliculasService.buscarPeliculas(this.criteriosBusqueda.nombre, this.criteriosBusqueda.salaId).subscribe({
      next: (response) => {
        this.resultadosBusqueda = response;
      },
      error: (err: any) => {
        console.error('Error al buscar películas', err);
        alert('Error en la búsqueda: ' + (err.error?.message || 'Verifica la consola.'));
      }
    });
  }
}
