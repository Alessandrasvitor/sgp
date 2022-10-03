import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';

import { ConfirmationService, MessageService } from 'primeng/api';
import { ConfirmDialogModule } from 'primeng/confirmdialog';
import { ToastModule } from 'primeng/toast';

import { ExcelService } from './shared/service/excel.service';
import { FeaturesModule } from './features/features.module';
import { SecurityModule } from './security/security.module';
import { AppRoutingModule } from './app.routing.module';
import { SharedModule } from './shared/shared.module';
import { AppComponent } from './app.component';

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
    SharedModule,
    FeaturesModule
  ],
  providers: [MessageService, ConfirmationService, ExcelService ],
  bootstrap: [AppComponent]
})
export class AppModule { }
