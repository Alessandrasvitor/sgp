import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { CategoryPipe } from './category.pipe';
import { StatusPipe } from './status.pipe';
import { CapitalizedPipe } from './capitalized.pipe';
import { MenuPipe } from './menu.pipe';

@NgModule({
  imports: [
    CommonModule
  ],
  declarations: [
      CategoryPipe,
      StatusPipe,
      CapitalizedPipe,
      MenuPipe
   ],
   exports: [
    CategoryPipe,
    StatusPipe,
    CapitalizedPipe,
    MenuPipe
   ]
})
export class PipesModule { }
