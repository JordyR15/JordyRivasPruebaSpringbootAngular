import { inject } from '@angular/core';
import { CanActivateFn, Router } from '@angular/router';

export const authGuard: CanActivateFn = (route, state) => {
  const router = inject(Router);

  // Verifica si el token existe en el localStorage
  if (localStorage.getItem('token')) {
    return true; // El usuario puede acceder a la ruta
  } else {
    // Si no hay token, redirige al login y bloquea el acceso
    router.navigate(['/login']);
    return false;
  }
};

