import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { Router } from '@angular/router';
import { SalasService } from '../../services/salas.service';

@Component({
  selector: 'app-add-sala',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './add-sala.component.html',
  styleUrls: ['./add-sala.component.css']
})
export class AddSalaComponent {

  sala = {
    nombre: ''
  };
  errorMessage: string | null = null;

  constructor(
    private salasService: SalasService,
    public router: Router
  ) { }

  onSubmit(): void {
    this.errorMessage = null;

    if (!this.sala.nombre.trim()) {
      this.errorMessage = 'Por favor, ingrese el nombre de la sala.';
      return;
    }

    this.salasService.addSala(this.sala).subscribe({
      next: (response) => {
        alert(`¡La sala "${response.nombre}" ha sido creada correctamente!`);
        this.router.navigate(['/dashboard']);
      },
      error: (err: any) => {
        console.error('Error al agregar la sala', err);
        this.errorMessage = err.error?.message || 'Ocurrió un error al guardar la sala.';
      }
    });
  }
}
