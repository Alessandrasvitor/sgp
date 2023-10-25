import { Pipe, PipeTransform } from '@angular/core';

@Pipe({
  name: 'typeLottery'
})
export class TypeLotteryPipe implements PipeTransform {

    transform(value: any, args?: any): any {
        switch ( value ) {
            case 'MEGA_SENA':
                return 'Mega Sena';
            case 'LOTOFACIL':
                return 'Lotofácil';
            case 'QUINA':
                return 'Quina';
            case 'LOTOMANIA':
                return 'Lotomania';
            case 'DUPLA_SENA':
                return 'Dupla Sena';
            case 'LOTECA':
                return 'Loteca';
            default:
                return '-';
        }
    }

}
