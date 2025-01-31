import { CanActivateFn, Router } from '@angular/router';
import { inject } from '@angular/core';
import { LocalStorageService } from '../services/local-storage.service';

export const authGuard: CanActivateFn = (route, state) => {
  const store = inject(LocalStorageService); // Service'i inject et
  const router = inject(Router); // Router'ı inject et

  // Kullanıcının auth-key'ine göre yetki kontrolü
  const isLoggedIn = !!store.get('auth-key');
  console.log("JWT Key::" + store.get('auth-key'));

  if (isLoggedIn) {
    return true; // Kullanıcı giriş yaptıysa, route'a erişime izin ver
  } else {
    router.navigate(['/login']); // Giriş yapılmamışsa login sayfasına yönlendir
    return false;
  }
};
