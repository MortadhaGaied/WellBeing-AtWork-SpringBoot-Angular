import { Injectable } from '@angular/core';
import { CanActivate, Router } from '@angular/router';
import { TokenStorageService } from '../services/token-storage.service';

@Injectable({
  providedIn: 'root',
})
export class AuthenticationGuard implements CanActivate {
  constructor(
    private tokenService: TokenStorageService,
    private router: Router
  ) {}

  canActivate() {
    if (this.tokenService.getToken()) {
      return true;
    } else {
      this.router.navigate(['/login']);
      return false;
    }
  }
}
