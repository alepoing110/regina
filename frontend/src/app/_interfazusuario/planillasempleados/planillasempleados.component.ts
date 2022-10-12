import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { NgbModal, NgbModalConfig } from '@ng-bootstrap/ng-bootstrap';
import { Empleados } from 'src/app/_modelo/empleados';
import { Planillasempleados } from 'src/app/_modelo/planillasempleados';
import { Unidades } from 'src/app/_modelo/unidades';
import { EmpleadosService } from 'src/app/_servicio/empleados.service';
import { PlanillasempleadosService } from 'src/app/_servicio/planillasempleados.service';
import swal from 'sweetalert2';

@Component({
  selector: 'app-planillasempleados',
  templateUrl: './planillasempleados.component.html',
  styles: [
  ]
})
export class PlanillasempleadosComponent implements OnInit {

  entidad: string = 'Planillas empleados'
  titulo: string = 'Planillas empleados';
  descripcion: string = 'Planillas empleados';
  opcion: string = 'planillas empleados';
  icono: string = 'list';

  datos: Planillasempleados[];
  dato: Planillasempleados;

  datos_empleados: Empleados[];

  idplanilla: number;

  estado: string = '';

  formulario: FormGroup;
  submitted: boolean = false;

  constructor(
    private _ruta: Router,
    private _route: ActivatedRoute,
    private _planillasempleadosService: PlanillasempleadosService,
    private _empleadosService: EmpleadosService,
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
    this.idplanilla = this._route.snapshot.params['id'];
    this.fdatos(this.idplanilla);
    this.fdatos_empleados();
  }

  fdatos_empleados() {
    this._empleadosService.buscarTodos().subscribe((data) => {
      this.datos_empleados = data;
    });
  }

  fdatos(id: number) {
    this._planillasempleadosService.buscarTodos(id).subscribe((data) => {
      this.datos = data;
    });
  }

  fformulario(dato: Planillasempleados) {
    this.formulario = this._fb.group({
      idempleado: [dato.idempleado, [Validators.required]],
      dias: [dato.dias, [Validators.required]],
      horas: [dato.horas, [Validators.required]],
    });
  }

  get f() {
    return this.formulario.controls;
  }

  fadicionar(content: any) {
    this.estado = 'Adicionar';
    this.dato = new Planillasempleados();
    this.dato.dias=30;
    this.dato.horas=8;
    this.fformulario(this.dato);
    this._modalService.open(content);
  }

  fmodificar(id: number, content: any) {
    this.estado = 'Modificar';
    this._planillasempleadosService.buscarPorId(id).subscribe((data) => {
      this.dato = data;
      this.fformulario(this.dato);
      this._modalService.open(content);
    });
  }

  faceptar(): void {
    this.submitted = true;
    this.dato.idplanilla = this.idplanilla;
    this.dato.idempleado = this.formulario.value.idempleado;
    this.dato.dias = this.formulario.value.dias;
    this.dato.horas = this.formulario.value.horas;
    if (this.estado === 'Modificar') {
      this._planillasempleadosService.modificar(this.dato).subscribe(
        (data) => {
          this.fdatos(this.idplanilla);
          this._modalService.dismissAll();
          swal.fire('Dato modificado', 'Dato modificado con exito', 'success');
        },
        (error) => {
          swal.fire('Error', 'Dato duplicado', 'error');
        }
      );
    } else {
      this._planillasempleadosService.adicionar(this.dato).subscribe(
        (data) => {
          this._modalService.dismissAll();
          this.fdatos(this.idplanilla);
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
          this._planillasempleadosService.borrar(id).subscribe((data) => {
            this.fdatos(this.idplanilla);
          });
        }
      });
  }

  fcancelar() {
    this._modalService.dismissAll();
  }

}
