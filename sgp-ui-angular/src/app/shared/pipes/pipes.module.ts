import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { CategoryPipe } from './category.pipe';
import { StatusPipe } from './status.pipe';

@NgModule({
  imports: [
    CommonModule
  ],
  declarations: [	
      CategoryPipe,
      StatusPipe
   ],
   exports: [    
    CategoryPipe,
    StatusPipe
   ]
})
export class PipesModule { }
