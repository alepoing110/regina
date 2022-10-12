import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { NgbModal, NgbModalConfig } from '@ng-bootstrap/ng-bootstrap';
import { Unidades } from 'src/app/_modelo/unidades';
import { UnidadesService } from 'src/app/_servicio/unidades.service';
import swal from 'sweetalert2';

@Component({
  selector: 'app-unidades',
  templateUrl: './unidades.component.html',
  styles: [
  ]
})
export class UnidadesComponent implements OnInit {

  entidad: string = 'Unidades'
  titulo: string = 'Unidades';
  descripcion: string = 'Unidades';
  opcion: string = 'unidades';
  icono: string = 'list';

  datos: Unidades[];
  dato: Unidades;

  pagina: number = 0;
  numPaginas: number = 0;
  cantidad: number = 5;
  buscar: string = '';
  total: number = 0;
  estado: string = '';

  formulario: FormGroup;
  submitted: boolean = false;

  constructor(
    private _ruta: Router,
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
    this.fdatos();
  }

  fcantidad() {
    this._unidadesService.cantidadBuscarPorTermino(this.buscar).subscribe((data) => {
      this.total = data;
    });
  }

  fbuscar(buscar: string) {
    this.buscar = buscar;
    this.pagina = 0;
    this.fdatos();
  }

  fdatos() {
    this._unidadesService.buscarPorTermino(this.buscar, this.pagina, this.cantidad).subscribe((data) => {
      this.fcantidad();
      this.datos = data;
    });
  }

  limpiar() {
    this.pagina = 0;
    this.buscar = '';
    this.fdatos();
  }

  mostrarMas(evento: any) {
    this.pagina = evento;
    this.fdatos();
  }

  fcambiarcantidad(cantidad: number) {
    this.cantidad=cantidad;
    this.pagina = 0;
    this.buscar = '';
    this.fdatos();
  }

  fformulario(dato: Unidades) {
    this.formulario = this._fb.group({
      unidad: [dato.unidad, [Validators.required]],
      sigla: [dato.sigla, [Validators.required]]
    });
  }

  get f() {
    return this.formulario.controls;
  }

  fadicionar(content: any) {
    this.estado = 'Adicionar';
    this.dato = new Unidades();
    this.fformulario(this.dato);
    this._modalService.open(content);
  }

  fmodificar(id: number, content: any) {
    this.estado = 'Modificar';
    this._unidadesService.buscarPorId(id).subscribe((data) => {
      this.dato = data;
      this.fformulario(this.dato);
      this._modalService.open(content);
    });
  }

  faceptar(): void {
    this.submitted = true;
    this.dato.unidad = this.formulario.value.unidad.toUpperCase();
    this.dato.sigla = this.formulario.value.sigla.toUpperCase();
    if (this.estado === 'Modificar') {
      this._unidadesService.modificar(this.dato).subscribe(
        (data) => {
          this.fdatos();
          this._modalService.dismissAll();
          swal.fire('Dato modificado', 'Dato modificado con exito', 'success');
        },
        (error) => {
          swal.fire('Error', 'Dato duplicado', 'error');
        }
      );
    } else {
      this._unidadesService.adicionar(this.dato).subscribe(
        (data) => {
          this.fdatos();
          this._modalService.dismissAll();
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
          this._unidadesService.borrar(id).subscribe((data) => {
            this.fdatos();
          });
        }
      });
  }

  fcancelar() {
    this._modalService.dismissAll();
  }

  fgeneraPDF() {
    this._unidadesService.generaPDF().subscribe( data=> {
      const url = window.URL.createObjectURL(data);
      const a = document.createElement("a");
      a.setAttribute("style", "display:none;");
      document.body.appendChild(a);
      a.href = url;
      a.download = "Reportes.pdf";
      a.click();
      return url;
    })
  }

  fgeneraXLS() {
    this._unidadesService.generaXLS().subscribe( data=> {
      const url = window.URL.createObjectURL(data);
      const a = document.createElement("a");
      a.setAttribute("style", "display:none;");
      document.body.appendChild(a);
      a.href = url;
      a.download = "Reportes.xlsx";
      a.click();
      return url;
    })
  }


}
