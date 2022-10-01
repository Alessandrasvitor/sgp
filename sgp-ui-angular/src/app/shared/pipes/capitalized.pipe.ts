import { Pipe, PipeTransform } from '@angular/core';

@Pipe({
  name: 'capitalized'
})
export class CapitalizedPipe implements PipeTransform {

  transform(value: any, args?: any): any {
    return value[0].toUpperCase() + value.substr(1).toLowerCase();
  }

}
