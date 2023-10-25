import { Pipe, PipeTransform } from '@angular/core';

@Pipe({
  name: 'menu'
})
export class MenuPipe implements PipeTransform {

  transform(value: unknown, ...args: unknown[]): any {
    switch ( value ) {
      case 'USER':
        return {label: 'Usuários', icon: 'pi pi-users', routerLink: '/user'};
      case 'COURSE':
        return {label: 'Curso', icon: 'pi pi-book', routerLink: '/course'};
      case 'INSTITUITION':
        return {label: 'Instituição', icon: 'pi pi-globe', routerLink: '/instituition'};
      case 'LOTTERY':
        return {label: 'Loterias', icon: 'pi pi-dollar', routerLink: '/lottery'};
    }
  }

}
