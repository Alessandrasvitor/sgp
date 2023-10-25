import { Pipe, PipeTransform } from '@angular/core';

@Pipe({
  name: 'formatCoin'
})
export class FormatCoinPipe implements PipeTransform {

  transform(value: any): string {
    if (!value) {
      return 'R$0,00';
    }

    let valorString = '' + value;

    valorString = valorString.replace('.', '-');

    let i = valorString.indexOf(',');
    while (i >= 0) {
      valorString = valorString.replace(',', '.');
      i = valorString.indexOf(',');
    }
    valorString = valorString.replace('-', ',');
    return valorString;
  }

}
