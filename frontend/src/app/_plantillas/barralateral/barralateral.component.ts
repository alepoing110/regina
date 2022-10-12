import { AccesoService } from 'src/app/_servicio/acceso.service';
import { MenusService } from '../../_servicio/menus.service';
import { Menus } from 'src/app/_modelo/menus';
import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-barralateral',
  templateUrl: './barralateral.component.html',
  styleUrls: ['./barralateral.component.css'],
})
export class BarralateralComponent implements OnInit {

  menus: Menus[];
  nombre: string;

  constructor(
    private _menusService: MenusService,
    private _accesoService: AccesoService
    ) {}

  ngOnInit() {
    this.fmenus();
    this.fdatosusuario();
  }

  fmenus() {
    this._menusService.datos().subscribe((data) => {
      this.menus = data;
    });
  }

  fdatosusuario() {
    this.nombre=this._accesoService.nombreLogueado();
  }

}
