import { Component, OnInit, OnDestroy } from '@angular/core'; // <-- 1. IMPORTA OnInit y OnDestroy
import { CommonModule } from '@angular/common';
import { RouterModule, Router } from '@angular/router';
import { AuthService } from '../services/auth.service';

@Component({
  selector: 'app-layout',
  standalone: true,
  imports: [CommonModule, RouterModule],
  templateUrl: './layout.component.html',
  styleUrls: ['./layout.component.css']
})
export class LayoutComponent implements OnInit, OnDestroy { // <-- 2. IMPLEMENTA LAS INTERFACES

  constructor(
    private authService: AuthService,
    private router: Router
  ) {}

  // --- 3. AÑADE ESTOS DOS MÉTODOS ---

  /**
   * Este método se ejecuta cuando el componente se carga por primera vez.
   */
  ngOnInit(): void {
    // Añadimos las clases necesarias al body para que el layout de AdminLTE funcione
    document.body.classList.add('layout-fixed', 'sidebar-mini');
  }

  /**
   * Este método se ejecuta justo antes de que el componente se destruya (ej: al cerrar sesión).
   */
  ngOnDestroy(): void {
    // Limpiamos las clases del body para que no afecten a otras páginas (como el login)
    document.body.classList.remove('layout-fixed', 'sidebar-mini');
  }

  logout() {
    localStorage.removeItem('token');
    this.router.navigate(['/login']);
  }
}
