import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { ToastModule } from 'primeng/toast';
import { MessageService } from 'primeng/api';

import { AppComponent } from './app.component';
import { AppRoutingModule } from './app.routing.module';
import { SecurityModule } from './security/security.module';
import { JwtHelperService } from '@auth0/angular-jwt';

@NgModule({
  declarations: [	
    AppComponent,
   ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    ToastModule,
    SecurityModule,
  ],
  providers: [
    MessageService,
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
