import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { CategoryPipe } from './category.pipe';
import { StatusPipe } from './status.pipe';
import { CapitalizedPipe } from './capitalized.pipe';
import { MenuPipe } from './menu.pipe';
import { FuncionalityPipe } from './funcionality.pipe';
import { TypeLotteryPipe } from './type-lottery.pipe';
import { FormatCoinPipe } from './format-coin.pipe';
import { MaxNumberLotteryPipe } from './max-number-lottery.pipe';

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
      TypeLotteryPipe,
      FormatCoinPipe,
      MaxNumberLotteryPipe
   ],
   exports: [
    CategoryPipe,
    StatusPipe,
    CapitalizedPipe,
    MenuPipe,
    FuncionalityPipe,
    TypeLotteryPipe,
    FormatCoinPipe,
    MaxNumberLotteryPipe
   ]
})
export class PipesModule { }
