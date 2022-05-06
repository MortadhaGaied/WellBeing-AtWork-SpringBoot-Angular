import { Injectable } from '@angular/core';
import {
  ActivatedRouteSnapshot,
  CanActivate,
  Router,
  RouterStateSnapshot,
  UrlTree,
} from '@angular/router';
import { Observable } from 'rxjs';
import { AuthService } from '../../../../../../Documents/gestion-rh-frontend-main/src/app/services/auth.service';
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
