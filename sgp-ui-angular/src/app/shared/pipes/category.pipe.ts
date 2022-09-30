import { Pipe, PipeTransform } from '@angular/core';

@Pipe({
  name: 'category'
})
export class CategoryPipe implements PipeTransform {

    transform(value: any, args?: any): any {
        switch ( value ) {
            case 'ADMINISTRACAO':
                return 'Administração';
            case 'AGRICULTURA':
                return 'Agricultura';
            case 'ARTE':
                return 'Arte';
            case 'CIENCIAS_EXATAS':
                return 'Ciências Exatas';
            case 'CIENCIAS_HUMANAS':
                return 'Ciências Humanas';
            case 'DIREITO':
                return 'Direito';
            case 'EDUCACAO':
                return 'Educação';
            case 'GASTRONOMIA':
                return 'Gastronomia';
            case 'IDIOMAS':
                return 'Idiomas';
            case 'INFORMATICA':
                return 'Informática';
            case 'MODA_BELEZA':
                return 'Moda e Beleza';
            case 'MUSICA':
                return 'Música';
            case 'SAUDE':
                return 'Saúde';
            case 'SEXUALIDADE':
                return 'Sexualidade';
            case 'CONCURSO':
                return 'Concurso';
            case 'OUTROS':
                return 'Outros';
            default:
                return '-';
        }
    }

}
