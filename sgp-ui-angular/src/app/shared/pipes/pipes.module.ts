import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { CategoryPipe } from './category.pipe';
import { StatusPipe } from './status.pipe';
import { CapitalizedPipe } from './capitalized.pipe';
import { MenuPipe } from './menu.pipe';
import { FuncionalityPipe } from './funcionality.pipe';

@NgModule({
  imports: [
    CommonModule
  ],
  declarations: [	
      CategoryPipe,
      StatusPipe,
      CapitalizedPipe,
      MenuPipe,
      FuncionalityPipe,
      
   ],
   exports: [
    CategoryPipe,
    StatusPipe,
    CapitalizedPipe,
    MenuPipe,
    FuncionalityPipe
   ]
})
export class PipesModule { }
