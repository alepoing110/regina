import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { NgbModal, NgbModalConfig } from '@ng-bootstrap/ng-bootstrap';
import { Actividadesitems } from 'src/app/_modelo/actividadesitems';
import { Items } from 'src/app/_modelo/items';
import { Unidades } from 'src/app/_modelo/unidades';
import { ActividadesitemsService } from 'src/app/_servicio/actividadesitems.service';
import { ItemsService } from 'src/app/_servicio/items.service';
import { UnidadesService } from 'src/app/_servicio/unidades.service';
import swal from 'sweetalert2';

@Component({
  selector: 'app-actividadesitems',
  templateUrl: './actividadesitems.component.html',
  styles: [
  ]
})
export class ActividadesitemsComponent implements OnInit {

  entidad: string = 'Actividadesitems'
  titulo: string = 'Actividadesitems';
  descripcion: string = 'Actividadesitems';
  opcion: string = 'actividadesitems';
  icono: string = 'list';

  datos: Actividadesitems[];
  dato: Actividadesitems;

  datos_unidades: Unidades[];
  datos_items: Items[];

  idactividad: number;

  estado: string = '';

  formulario: FormGroup;
  submitted: boolean = false;

  constructor(
    private _ruta: Router,
    private _route: ActivatedRoute,
    private _actividadesitemsService: ActividadesitemsService,
    private _unidadesService: UnidadesService,
    private _itemsService: ItemsService,
    private _fb: FormBuilder,
    private _modalService: NgbModal,
    config: NgbModalConfig
  ) {
    config.backdrop = 'static';
    config.keyboard = false;
    config.size = 'md';
  }

  finicio() {}

  ngOnInit(): void {
    this.idactividad = this._route.snapshot.params['id'];
    this.fdatos(this.idactividad);
    this.fdatos_unidades();
    this.fdatos_items();
  }

  fdatos_unidades() {
    this._unidadesService.buscarTodos().subscribe((data) => {
      this.datos_unidades = data;
    });
  }

  fdatos_items() {
    this._itemsService.buscarTodos().subscribe((data) => {
      this.datos_items = data;
    });
  }

  fdatos(id: number) {
    this._actividadesitemsService.buscarTodos(id).subscribe((data) => {
      this.datos = data;
    });
  }

  fformulario(dato: Actividadesitems) {
    this.formulario = this._fb.group({
      iditem: [dato.iditem, [Validators.required]],
      idunidad: [dato.idunidad, [Validators.required]],
      cantidad: [dato.cantidad, [Validators.required]],
      costounitario: [dato.costounitario, [Validators.required]]
    });
  }

  get f() {
    return this.formulario.controls;
  }

  fadicionar(content: any) {
    this.estado = 'Adicionar';
    this.dato = new Actividadesitems();
    this.dato.costounitario=0;
    this.fformulario(this.dato);
    this._modalService.open(content);
  }

  fmodificar(id: number, content: any) {
    this.estado = 'Modificar';
    this._actividadesitemsService.buscarPorId(id).subscribe((data) => {
      this.dato = data;
      this.fformulario(this.dato);
      this._modalService.open(content);
    });
  }

  faceptar(): void {
    this.submitted = true;
    this.dato.idactividad = this.idactividad;
    this.dato.iditem = this.formulario.value.iditem;
    this.dato.idunidad = this.formulario.value.idunidad;
    this.dato.cantidad = this.formulario.value.cantidad;
    this.dato.costounitario = this.formulario.value.costounitario;
    if (this.estado === 'Modificar') {
      this._actividadesitemsService.modificar(this.dato).subscribe(
        (data) => {
          this.fdatos(this.idactividad);
          this._modalService.dismissAll();
          swal.fire('Dato modificado', 'Dato modificado con exito', 'success');
        },
        (error) => {
          swal.fire('Error', 'Dato duplicado', 'error');
        }
      );
    } else {
      this._actividadesitemsService.adicionar(this.dato).subscribe(
        (data) => {
          this._modalService.dismissAll();
          this.fdatos(this.idactividad);
          swal.fire('Dato adicionado', 'Dato adicionado con exito', 'success');
        },
        (error) => {
          swal.fire('Error', 'Dato duplicado', 'error');
        }
      );
    }
  }

  feliminar(id: number) {
    swal
      .fire({
        title: 'Estás seguro?',
        icon: 'warning',
        text: 'No podrás revertir el borrado de este dato!',
        showCancelButton: true,
        cancelButtonText: 'Cancelar',
        confirmButtonText: 'Borrar',
      })
      .then((result) => {
        if (result.value) {
          this._actividadesitemsService.borrar(id).subscribe((data) => {
            this.fdatos(this.idactividad);
          });
        }
      });
  }

  fcancelar() {
    this._modalService.dismissAll();
  }

}
