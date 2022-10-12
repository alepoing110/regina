import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { NgbModal, NgbModalConfig } from '@ng-bootstrap/ng-bootstrap';
import { Proveedores } from 'src/app/_modelo/proveedores';
import { ProveedoresService } from 'src/app/_servicio/proveedores.service';
import swal from 'sweetalert2';

@Component({
  selector: 'app-proveedores',
  templateUrl: './proveedores.component.html',
  styles: [
  ]
})
export class ProveedoresComponent implements OnInit {

  entidad: string = 'Proveedores'
  titulo: string = 'Proveedores';
  descripcion: string = 'Proveedores';
  opcion: string = 'proveedores';
  icono: string = 'list';

  datos: Proveedores[];
  dato: Proveedores;

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
    private _proveedoresService: ProveedoresService,
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
    this._proveedoresService.cantidadBuscarPorTermino(this.buscar).subscribe((data) => {
      this.total = data;
    });
  }

  fbuscar(buscar: string) {
    this.buscar = buscar;
    this.pagina = 0;
    this.fdatos();
  }

  fdatos() {
    this._proveedoresService.buscarPorTermino(this.buscar, this.pagina, this.cantidad).subscribe((data) => {
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

  fformulario(dato: Proveedores) {
    this.formulario = this._fb.group({
      proveedor: [dato.proveedor, [Validators.required]],
      nit: [dato.nit, [Validators.required]],
      direccion: [dato.direccion, [Validators.required]],
      telefono: [dato.telefono, [Validators.required]],
      celular: [dato.celular, [Validators.required]],
      correo: [dato.correo, [Validators.required]],
    });
  }

  get f() {
    return this.formulario.controls;
  }

  fadicionar(content: any) {
    this.estado = 'Adicionar';
    this.dato = new Proveedores();
    this.fformulario(this.dato);
    this._modalService.open(content);
  }

  fmodificar(id: number, content: any) {
    this.estado = 'Modificar';
    this._proveedoresService.buscarPorId(id).subscribe((data) => {
      this.dato = data;
      this.fformulario(this.dato);
      this._modalService.open(content);
    });
  }

  faceptar(): void {
    this.submitted = true;
    this.dato.proveedor = this.formulario.value.proveedor.toUpperCase();
    this.dato.nit = this.formulario.value.nit;
    this.dato.direccion = this.formulario.value.direccion.toUpperCase();
    this.dato.telefono = this.formulario.value.telefono.toUpperCase();
    this.dato.celular = this.formulario.value.celular.toUpperCase();
    this.dato.correo = this.formulario.value.correo;
    if (this.estado === 'Modificar') {
      this._proveedoresService.modificar(this.dato).subscribe(
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
      this._proveedoresService.adicionar(this.dato).subscribe(
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
          this._proveedoresService.borrar(id).subscribe((data) => {
            this.fdatos();
          });
        }
      });
  }

  fcancelar() {
    this._modalService.dismissAll();
  }

  fgeneraPDF() {
    this._proveedoresService.generaPDF().subscribe( data=> {
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
    this._proveedoresService.generaXLS().subscribe( data=> {
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
