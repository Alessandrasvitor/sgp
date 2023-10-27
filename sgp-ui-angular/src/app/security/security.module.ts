import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { BrowserModule } from '@angular/platform-browser';
import { FormsModule } from '@angular/forms';
import { RouterModule } from '@angular/router';
import { HttpClientModule } from '@angular/common/http';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';

import { LoginComponent } from './login/login.component';
import { ErrorService } from '../shared/service/error.service';
import { RegisterComponent } from './register/register.component';
import { ResetPwdComponent } from './reset-pwd/reset-pwd.component';
import { ActiveUserComponent } from './active-user/active-user.component';

import { PasswordModule } from 'primeng/password';
import { InputTextModule } from 'primeng/inputtext';
import { ButtonModule } from 'primeng/button';
import { CardModule } from 'primeng/card';
import { MessagesModule } from 'primeng/messages';
import { MessageModule } from 'primeng/message';
import { ProgressSpinnerModule } from 'primeng/progressspinner';


@NgModule({
  declarations: [
    LoginComponent,
    RegisterComponent,
    ResetPwdComponent,
    ActiveUserComponent
  ],
  imports: [
    CommonModule,
    BrowserModule,
    FormsModule,
    BrowserAnimationsModule,
    HttpClientModule,
    PasswordModule,
    InputTextModule,
    ButtonModule,
    RouterModule,
    CardModule,
    MessagesModule,
    MessageModule,
    ProgressSpinnerModule
  ],
  providers: [
    ErrorService,
  ]
})
export class SecurityModule { }
