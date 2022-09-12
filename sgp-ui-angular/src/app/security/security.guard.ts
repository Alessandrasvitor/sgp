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

    /*
    if (this.auth.isLoggedIn !== true) {
      console.log('Navegação com access token inválido.');
      this.router.navigate(['/login']);
      return false;
    }
    let route = next.routeConfig.path;
    if (route.indexOf('/') >= 0) {
      route = JSON.parse(JSON.stringify(route.slice(0, route.indexOf('/')) + 's'));
    }
    if (route === 'produtos' || route === 'veiculos' || route === 'imoveis') {
      let loja;
      this.configService.lojaLocalStorage().then(res => {
        loja = res;
        if (route.toUpperCase() === loja.negocio || (route.toUpperCase() === 'PRODUTOS' && loja.negocio === 'OUTROS')) {
          route = 'produtos';
        } else {
          route = 'outros';
        }
      });
    }
    if(route === 'rede') {
      route = 'configuracoes';
    }
    if (!this.configService.validarAcessoTela(route.toUpperCase())) {
      this.router.navigate(['/pagina-nao-encontrada']);
    }
    */
    return true;
  }

}
