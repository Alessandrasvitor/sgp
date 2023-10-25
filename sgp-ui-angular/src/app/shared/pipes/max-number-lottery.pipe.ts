import { Pipe, PipeTransform } from '@angular/core';

@Pipe({
  name: 'max-number-lottery'
})
export class MaxNumberLotteryPipe implements PipeTransform {

  transform(value: any, args?: any): any {
    switch ( value ) {
        case 'MEGA_SENA':
            return 60;
        case 'LOTOFACIL':
            return 25;
        case 'QUINA':
            return 80;
        case 'LOTOMANIA':
            return 100;
        case 'DUPLA_SENA':
            return 50;
        case 'LOTECA':
            return 25;
        default:
            return 0;
    }
}

}
