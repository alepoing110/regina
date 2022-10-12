import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { NgbModal, NgbModalConfig } from '@ng-bootstrap/ng-bootstrap';
import { Insumos } from 'src/app/_modelo/insumos';
import { Itemsinsumos } from 'src/app/_modelo/itemsinsumos';
import { Unidades } from 'src/app/_modelo/unidades';
import { InsumosService } from 'src/app/_servicio/insumos.service';
import { ItemsinsumosService } from 'src/app/_servicio/itemsinsumos.service';
import { UnidadesService } from 'src/app/_servicio/unidades.service';
import swal from 'sweetalert2';

@Component({
  selector: 'app-itemsinsumos',
  templateUrl: './itemsinsumos.component.html',
  styles: [
  ]
})
export class ItemsinsumosComponent implements OnInit {

  entidad: string = 'Itemsinsumos'
  titulo: string = 'Items Insumos';
  descripcion: string = 'Items Insumos';
  opcion: string = 'itemsinsumos';
  icono: string = 'list';

  datos: Itemsinsumos[];
  dato: Itemsinsumos;

  datos_unidades: Unidades[];
  datos_insumos: Insumos[];

  iditem: number;

  estado: string = '';

  formulario: FormGroup;
  submitted: boolean = false;

  constructor(
    private _ruta: Router,
    private _route: ActivatedRoute,
    private _itemsinsumosService: ItemsinsumosService,
    private _insumosService: InsumosService,
    private _unidadesService: UnidadesService,
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
    this.iditem = this._route.snapshot.params['id'];
    this.fdatos(this.iditem);
    this.fdatos_unidades();
    this.fdatos_insumos();
  }

  fdatos_unidades() {
    this._unidadesService.buscarTodos().subscribe((data) => {
      this.datos_unidades = data;
    });
  }

  fdatos_insumos() {
    this._insumosService.buscarTodos().subscribe((data) => {
      this.datos_insumos = data;
    });
  }

  fdatos(id: number) {
    this._itemsinsumosService.buscarTodos(id).subscribe((data) => {
      this.datos = data;
    });
  }

  fformulario(dato: Itemsinsumos) {
    this.formulario = this._fb.group({
      idunidad: [dato.idunidad, [Validators.required]],
      idinsumo: [dato.idinsumo, [Validators.required]],
      costounitario: [dato.costounitario, [Validators.required]],
    });
  }

  get f() {
    return this.formulario.controls;
  }

  fadicionar(content: any) {
    this.estado = 'Adicionar';
    this.dato = new Itemsinsumos();
    this.fformulario(this.dato);
    this._modalService.open(content);
  }

  fmodificar(id: number, content: any) {
    this.estado = 'Modificar';
    this._itemsinsumosService.buscarPorId(id).subscribe((data) => {
      this.dato = data;
      this.fformulario(this.dato);
      this._modalService.open(content);
    });
  }

  faceptar(): void {
    this.submitted = true;
    this.dato.iditem = this.iditem;
    this.dato.idunidad = this.formulario.value.idunidad;
    this.dato.idinsumo = this.formulario.value.idinsumo;
    this.dato.costounitario = this.formulario.value.costounitario;
    if (this.estado === 'Modificar') {
      this._itemsinsumosService.modificar(this.dato).subscribe(
        (data) => {
          this.fdatos(this.iditem);
          this._modalService.dismissAll();
          swal.fire('Dato modificado', 'Dato modificado con exito', 'success');
        },
        (error) => {
          swal.fire('Error', 'Dato duplicado', 'error');
        }
      );
    } else {
      this._itemsinsumosService.adicionar(this.dato).subscribe(
        (data) => {
          this._modalService.dismissAll();
          this.fdatos(this.iditem);
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
          this._itemsinsumosService.borrar(id).subscribe((data) => {
            this.fdatos(this.iditem);
          });
        }
      });
  }

  fcancelar() {
    this._modalService.dismissAll();
  }

}

