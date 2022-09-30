import { Pipe, PipeTransform } from '@angular/core';

@Pipe({
  name: 'status'
})
export class StatusPipe implements PipeTransform {

  transform(value: any, args?: any): any {
    switch ( value ) {
      case 'FAIL':
        return 'Reprovado';
      case 'FINISH':
        return 'Finalizado';
      case 'PENDING':
        return 'Pendente';
      case 'PROGRESS':
        return 'Em prograsso';
      default: 
        return '-';
    }
  }
}
