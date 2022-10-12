import { Router } from '@angular/router';
import { Component, OnInit, Input } from '@angular/core';

@Component({
  selector: 'app-cabeceratitulos',
  templateUrl: './cabeceratitulos.component.html',
  styleUrls: ['./cabeceratitulos.component.css']
})
export class CabeceratitulosComponent {

  @Input() titulo: string;
  @Input() descripcion: string;
  @Input() icono: string;
  @Input() opcion: string;

  constructor(private _ruta: Router) { }

  finicio(){
    this._ruta.navigateByUrl('escritorio');
  }

}
