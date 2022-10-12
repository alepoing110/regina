import { E404Component } from './e404/e404.component';
import { PlantillasModule } from './_plantillas/plantillas.module';
import { BrowserModule } from '@angular/platform-browser';
import { LOCALE_ID, NgModule } from '@angular/core';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { AccesoComponent } from './acceso/acceso.component';

import { HttpClientModule, HTTP_INTERCEPTORS } from '@angular/common/http';
import { ReactiveFormsModule } from '@angular/forms';
import { NgbModule, NgbAlertModule, NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { ToastrModule } from 'ngx-toastr';
import { AccesoInterceptor } from './_configuracion/acceso.interceptor';

import localeEsBo from '@angular/common/locales/es-BO';
import { DatePipe, registerLocaleData } from '@angular/common';
import { UnidadesComponent } from './_interfazusuario/unidades/unidades.component';
registerLocaleData(localeEsBo, 'es-Bo');

@NgModule({
  declarations: [
    AppComponent,
    AccesoComponent,
    E404Component,
    UnidadesComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    HttpClientModule,
    ReactiveFormsModule,
    PlantillasModule,
    NgbModule,
    BrowserAnimationsModule,
    ToastrModule.forRoot(),
    NgbAlertModule
  ],
  providers: [
    {
      provide: LOCALE_ID, useValue: 'es-Bo'
    },
    {
      provide: HTTP_INTERCEPTORS,
      useClass: AccesoInterceptor,
      multi: true
    }
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
