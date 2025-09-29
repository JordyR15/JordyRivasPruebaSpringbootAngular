import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
// Ya no necesitamos importar AuthService, Router, ni RouterModule aquí

@Component({
  selector: 'app-dashboard',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './dashboard.component.html',
  styleUrls: ['./dashboard.component.css']
})
export class DashboardComponent {
  // Por ahora, este componente no necesita lógica propia.
  constructor() {}
}
