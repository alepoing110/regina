import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { NgbModal, NgbModalConfig } from '@ng-bootstrap/ng-bootstrap';
import { Personas } from 'src/app/_modelo/personas';
import { PersonasService } from 'src/app/_servicio/personas.service';
import swal from 'sweetalert2';

@Component({
  selector: 'app-personas',
  templateUrl: './personas.component.html',
  styles: [
  ]
})
export class PersonasComponent implements OnInit {

  entidad: string = 'Personas'
  titulo: string = 'Personas';
  descripcion: string = 'Personas';
  opcion: string = 'personas';
  icono: string = 'list';

  datos: Personas[];
  dato: Personas;

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
    private _personasService: PersonasService,
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
    this._personasService.cantidadBuscarPorTermino(this.buscar).subscribe((data) => {
      this.total = data;
    });
  }

  fbuscar(buscar: string) {
    this.buscar = buscar;
    this.pagina = 0;
    this.fdatos();
  }

  fdatos() {
    this._personasService.buscarPorTermino(this.buscar, this.pagina, this.cantidad).subscribe((data) => {
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

  fformulario(dato: Personas) {
    this.formulario = this._fb.group({
      primerapellido: [dato.primerapellido, [Validators.required]],
      segundoapellido: [dato.segundoapellido, [Validators.required]],
      primernombre: [dato.primernombre, [Validators.required]],
      segundonombre: [dato.segundonombre, [Validators.required]],
      dip: [dato.dip, [Validators.required]],
      direccion: [dato.direccion, [Validators.required]],
      telefono: [dato.telefono, [Validators.required]],
      celular: [dato.celular, [Validators.required]],
      correo: [dato.correo, [Validators.required]]
    });
  }

  get f() {
    return this.formulario.controls;
  }

  fadicionar(content: any) {
    this.estado = 'Adicionar';
    this.dato = new Personas();
    this.fformulario(this.dato);
    this._modalService.open(content);
  }

  fmodificar(id: number, content: any) {
    this.estado = 'Modificar';
    this._personasService.buscarPorId(id).subscribe((data) => {
      this.dato = data;
      this.fformulario(this.dato);
      this._modalService.open(content);
    });
  }

  faceptar(): void {
    this.submitted = true;
    this.dato.primerapellido = this.formulario.value.primerapellido.toUpperCase();
    this.dato.segundoapellido = this.formulario.value.segundoapellido.toUpperCase();
    this.dato.primernombre = this.formulario.value.primernombre.toUpperCase();
    this.dato.segundonombre = this.formulario.value.segundonombre.toUpperCase();
    this.dato.dip = this.formulario.value.dip;
    this.dato.direccion = this.formulario.value.direccion.toUpperCase();
    this.dato.telefono = this.formulario.value.telefono;
    this.dato.celular = this.formulario.value.celular;
    this.dato.correo = this.formulario.value.correo;
    if (this.estado === 'Modificar') {
      this._personasService.modificar(this.dato).subscribe(
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
      this._personasService.adicionar(this.dato).subscribe(
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
          this._personasService.borrar(id).subscribe((data) => {
            this.fdatos();
          });
        }
      });
  }

  fcancelar() {
    this._modalService.dismissAll();
  }

  fgeneraPDF() {
    this._personasService.generaPDF().subscribe( data=> {
      const url = window.URL.createObjectURL(data);
      const a = document.createElement("a");
      a.setAttribute("style", "display:none;");
      document.body.appendChild(a);
      a.href = url;
      a.download = "Unidades.pdf";
      a.click();
      return url;
    })
  }

  fgeneraXLS() {
    this._personasService.generaXLS().subscribe( data=> {
      const url = window.URL.createObjectURL(data);
      const a = document.createElement("a");
      a.setAttribute("style", "display:none;");
      document.body.appendChild(a);
      a.href = url;
      a.download = "Unidades.xlsx";
      a.click();
      return url;
    })
  }


}
