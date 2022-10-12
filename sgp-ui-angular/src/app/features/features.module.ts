import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { BrowserModule } from '@angular/platform-browser';
import { HttpClientModule } from '@angular/common/http';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { RouterModule } from '@angular/router';

import { InstitutionComponent } from './institution/institution.component';
import { CourseComponent } from './course/course.component';
import { PipesModule } from '../shared/pipes/pipes.module';
import { HomeComponent } from './home/home.component';
import { UserComponent } from './user/user.component';

import { TableModule } from 'primeng/table';
import { FieldsetModule } from 'primeng/fieldset';
import { ButtonModule } from 'primeng/button';
import { ToolbarModule } from 'primeng/toolbar';
import { RatingModule } from 'primeng/rating';
import { CardModule } from 'primeng/card';
import { InputTextModule } from 'primeng/inputtext';
import { MessageModule } from 'primeng/message';
import { MessagesModule } from 'primeng/messages';
import { PasswordModule } from 'primeng/password';
import { DropdownModule } from 'primeng/dropdown';
import { DialogModule } from 'primeng/dialog';
import { InputNumberModule } from 'primeng/inputnumber';
import { ImageModule } from 'primeng/image';
import { PickListModule } from 'primeng/picklist';
import { MultiSelectModule } from 'primeng/multiselect';
import { TooltipModule } from 'primeng/tooltip';

@NgModule({
  declarations: [
    InstitutionComponent,
    CourseComponent,
    HomeComponent,
    UserComponent
  ],
  imports: [
  CommonModule,
    BrowserModule,
    FormsModule,
    BrowserAnimationsModule,
    TableModule,
    ButtonModule,
    FieldsetModule,
    ToolbarModule,
    RatingModule,    
    HttpClientModule,
    PasswordModule,
    InputTextModule,
    RouterModule,
    CardModule,
    MessagesModule,
    MessageModule,
    PipesModule,
    InputTextModule,
    DialogModule,
    InputNumberModule,
    DropdownModule,
    MessageModule,
    ImageModule,
    PickListModule,
    MultiSelectModule,
    TooltipModule
  ],
  providers: [ ],
  exports: [ PipesModule ]
})
export class FeaturesModule { }
