import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { ToastModule } from 'primeng/toast';
import { ConfirmationService, MessageService } from 'primeng/api';

import { AppComponent } from './app.component';
import { AppRoutingModule } from './app.routing.module';
import { SecurityModule } from './security/security.module';
import { FeaturesModule } from './features/features.module';
import { ConfirmDialogModule } from 'primeng/confirmdialog';
import { ExcelService } from './shared/service/excel.service';

@NgModule({
  declarations: [	
    AppComponent,
   ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    ToastModule,
    ConfirmDialogModule,
    SecurityModule,
    FeaturesModule
  ],
  providers: [MessageService, ConfirmationService, ExcelService ],
  bootstrap: [AppComponent]
})
export class AppModule { }
