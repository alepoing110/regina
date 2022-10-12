import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { NgbModal, NgbModalConfig } from '@ng-bootstrap/ng-bootstrap';
import { Actividades } from 'src/app/_modelo/actividades';
import { Unidades } from 'src/app/_modelo/unidades';
import { ActividadesService } from 'src/app/_servicio/actividades.service';
import { UnidadesService } from 'src/app/_servicio/unidades.service';
import swal from 'sweetalert2';

@Component({
  selector: 'app-actividades',
  templateUrl: './actividades.component.html',
  styles: [
  ]
})
export class ActividadesComponent implements OnInit {

  entidad: string = 'Actividades'
  titulo: string = 'Actividades';
  descripcion: string = 'Actividades';
  opcion: string = 'actividades';
  icono: string = 'list';

  datos: Actividades[];
  dato: Actividades;

  datos_unidades: Unidades[];

  idproyecto: number;

  estado: string = '';

  formulario: FormGroup;
  submitted: boolean = false;

  constructor(
    private _ruta: Router,
    private _route: ActivatedRoute,
    private _actividadesService: ActividadesService,
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
    this.idproyecto = this._route.snapshot.params['id'];
    this.fdatos(this.idproyecto);
    this.fdatos_unidades();
  }

  fdatos_unidades() {
    this._unidadesService.buscarTodos().subscribe((data) => {
      this.datos_unidades = data;
    });
  }

  fdatos(id: number) {
    this._actividadesService.buscarTodos(id).subscribe((data) => {
      this.datos = data;
    });
  }

  fformulario(dato: Actividades) {
    this.formulario = this._fb.group({
      actividad: [dato.actividad, [Validators.required]],
      idunidad: [dato.idunidad, [Validators.required]],
      costounitario: [dato.costounitario, [Validators.required]],
    });
  }

  get f() {
    return this.formulario.controls;
  }

  fadicionar(content: any) {
    this.estado = 'Adicionar';
    this.dato = new Actividades();
    this.dato.costounitario=0;
    this.fformulario(this.dato);
    this._modalService.open(content);
  }

  fmodificar(id: number, content: any) {
    this.estado = 'Modificar';
    this._actividadesService.buscarPorId(id).subscribe((data) => {
      this.dato = data;
      this.fformulario(this.dato);
      this._modalService.open(content);
    });
  }

  faceptar(): void {
    this.submitted = true;
    this.dato.idproyecto = this.idproyecto;
    this.dato.actividad = this.formulario.value.actividad.toUpperCase();
    this.dato.idunidad = this.formulario.value.idunidad;
    this.dato.costounitario = this.formulario.value.costounitario;
    if (this.estado === 'Modificar') {
      this._actividadesService.modificar(this.dato).subscribe(
        (data) => {
          this.fdatos(this.idproyecto);
          this._modalService.dismissAll();
          swal.fire('Dato modificado', 'Dato modificado con exito', 'success');
        },
        (error) => {
          swal.fire('Error', 'Dato duplicado', 'error');
        }
      );
    } else {
      this._actividadesService.adicionar(this.dato).subscribe(
        (data) => {
          this._modalService.dismissAll();
          this.fdatos(this.idproyecto);
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
          this._actividadesService.borrar(id).subscribe((data) => {
            this.fdatos(this.idproyecto);
          });
        }
      });
  }

  fcancelar() {
    this._modalService.dismissAll();
  }

  fitems(id: number){
    const _ruta = '/actividades/' + id;
    this._ruta.navigateByUrl(_ruta);
  }

}
