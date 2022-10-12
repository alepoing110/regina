import { Router } from '@angular/router';
import { Component, OnInit } from '@angular/core';
import { AccesoService } from 'src/app/_servicio/acceso.service';
import { environment } from 'src/environments/environment';
import { Location } from '@angular/common';

@Component({
  selector: 'app-barrasuperior',
  templateUrl: './barrasuperior.component.html',
  styleUrls: ['./barrasuperior.component.css']
})
export class BarrasuperiorComponent implements OnInit {

  nombreapp: string = environment.nombreapp;

  constructor(
    private _accesoService: AccesoService,
    private _ruta: Router,
    private _location: Location
  ) { }

  ngOnInit(): void {
  }

  fsalir() {
    this._accesoService.cerrarSesion();
    this._ruta.navigate(['acceso']);
  }

  goBack(){
    this._location.back();
  }

  goForward(){
    this._location.forward();
  }

}
