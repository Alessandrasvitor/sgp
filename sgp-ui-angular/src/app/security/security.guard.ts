import { SecurityService } from './security.service';
import { Injectable } from '@angular/core';
import { CanActivate, ActivatedRouteSnapshot, Router } from '@angular/router';

@Injectable({
  providedIn: 'root'
})

export class SecurityGuard implements CanActivate {

  constructor(
    private security: SecurityService,
    private router: Router
  ) { }

  canActivate(next: ActivatedRouteSnapshot) {

    const user = JSON.parse(localStorage.getItem('userLogin')+'');
    if (!user) {
      this.router.navigate(['/login']);
      return false;
    }

    let route = next.routeConfig?.path;
    if(!route || route === 'instituition') {
      return true;
    }

    if(user.functionalities.filter((func: any) => func === route?.toUpperCase()).length <= 0) {
      this.router.navigate(['/pagina-nao-encontrada']);
    }

    return true;
  }

}
