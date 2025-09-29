import { Routes } from '@angular/router';

// --- IMPORTS QUE FALTABAN Y LOS QUE YA ESTABAN ---
import { LoginComponent } from './views/login/login.component';
import { ForgotPasswordComponent } from './views/forgot-password/forgot-password.component';
import { RegisterComponent } from './views/register/register.component';
import { LayoutComponent } from './layout/layout.component'; // <-- Corregido
import { authGuard } from './guards/auth.guard'; // <-- Corregido
import { AddPeliculaComponent } from './views/peliculas/add-pelicula.component';
import { ListPeliculaComponent } from './views/peliculas/list-pelicula.component';
import { EditPeliculaComponent } from './views/peliculas/edit-pelicula.component';
import { AddSalaComponent } from './views/salas/add-sala.component';
import { AsignarSalaComponent } from './views/salas/asignar-sala.component'; // <-- Corregido
import { DashboardComponent } from './views/dashboard/dashboard.component';
import { BuscarPeliculaComponent } from './views/peliculas/buscar-pelicula.component';
import { ReportePeliculasFechaComponent } from './views/reportes/reporte-peliculas-fecha.component';
// --- FIN DE LA SECCIÓN DE IMPORTS ---

export const routes: Routes = [
  // Rutas públicas
  { path: 'login', component: LoginComponent },
  { path: 'forgot-password', component: ForgotPasswordComponent },
  { path: 'register', component: RegisterComponent },

  // Rutas protegidas que usan el Layout
  {
    path: '',
    component: LayoutComponent,
    canActivate: [authGuard],
    children: [
      { path: 'dashboard', component: DashboardComponent },
      { path: 'peliculas/agregar', component: AddPeliculaComponent },
      { path: 'peliculas/listar', component: ListPeliculaComponent },
      { path: 'peliculas/editar/:id', component: EditPeliculaComponent },
      { path: 'peliculas/buscar', component: BuscarPeliculaComponent },
      { path: 'salas/agregar', component: AddSalaComponent },
      { path: 'programacion/asignar', component: AsignarSalaComponent },
      { path: 'reportes/por-fecha', component: ReportePeliculasFechaComponent },
      { path: '', redirectTo: 'dashboard', pathMatch: 'full' }
    ]
  },
  { path: '**', redirectTo: 'login' }
];
