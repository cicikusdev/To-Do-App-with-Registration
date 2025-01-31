import {HttpEvent, HttpHandler, HttpInterceptor, HttpInterceptorFn, HttpRequest} from '@angular/common/http';
import {inject, Injectable} from '@angular/core';
import { LocalStorageService } from '../services/local-storage.service';
import {Observable} from 'rxjs';

@Injectable()
export class AuthKeyInterceptor implements HttpInterceptor {

  constructor(
    private storageService: LocalStorageService
  ) {
  }

  // intercept(req: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
  //   const token = this.storageService.get('auth-key'); // auth-key'i al
  //   if (!token) {
  //     console.log('Token bulunamadı.');
  //   }
  //
  //   console.log("Authentication key:" + token);
  //
  //   if (token) {
  //     // Token varsa isteği klonla ve Authorization başlığını ekle
  //     req = req.clone({
  //       setHeaders: {
  //         Authorization: `Bearer ${token}`
  //       }
  //     });
  //   }
  //
  //   // İsteği bir sonraki handler'a ilet
  //   return next.handle(req);
  //
  //
  // }

  intercept(req: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
    const token = this.storageService.get('auth-key');
    if (token) {
      req = req.clone({
        setHeaders: {
          Authorization: `Bearer ${token}` // Bearer eklenmiş mi?
        }
      });
    }
    return next.handle(req);
  }

}
