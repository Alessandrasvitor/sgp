import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { NavBarComponent } from './nav-bar/nav-bar.component';
import { MenubarModule } from 'primeng/menubar';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { BrowserModule } from '@angular/platform-browser';
import { FormsModule } from '@angular/forms';
import { PipesModule } from './pipes/pipes.module';
import { SplitButtonModule } from 'primeng/splitbutton';
import { PaginatorModule } from 'primeng/paginator';
import { PaginatorComponent } from './components/paginator/paginator.component';

@NgModule({
  imports: [
    CommonModule,
    BrowserModule,
    FormsModule,
    BrowserAnimationsModule,
    MenubarModule,
    SplitButtonModule,
    PipesModule,
    PaginatorModule
  ],
  declarations: [
    NavBarComponent,
    PaginatorComponent
  ],
  exports: [
    NavBarComponent,
    PaginatorComponent
  ]
})
export class SharedModule { }
