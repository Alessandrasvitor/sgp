import { Pipe, PipeTransform } from '@angular/core';

@Pipe({
  name: 'funcionality'
})
export class FuncionalityPipe implements PipeTransform {

  transform(value: any, args?: any): any {
    
      switch ( value.toUpperCase() ) {
          case 'HOME':
              return 'Home';
          case 'COURSE':
              return 'Curso';
          case 'INSTITUITION':
              return 'Instituição';
          case 'USER':
              return 'Usuário';
          default:
              return '-';
      }
  }

}
