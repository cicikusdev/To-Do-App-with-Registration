import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class LocalStorageService {

  private memoryStorage = new Map<string, any>(); // Tarayıcı yoksa kullanılacak geçici depolama

  set(key: string, value: any): void {
    if (typeof window !== 'undefined' && window.localStorage) {
      localStorage.setItem(key, JSON.stringify(value));
    } else {
      this.memoryStorage.set(key, value); // SSR'de buraya kaydediyoruz
    }
  }

  get(key: string): any {
    if (typeof window !== 'undefined' && window.localStorage) {
      const value = localStorage.getItem(key);
      return value ? JSON.parse(value) : null;
    }
    return this.memoryStorage.get(key) || null; // Eğer tarayıcı yoksa, hafızadan çek
  }

  remove(key: string) {
    if (typeof window !== 'undefined' && window.localStorage) {
      localStorage.removeItem(key);
    } else {
      this.memoryStorage.delete(key);
    }
  }
}
