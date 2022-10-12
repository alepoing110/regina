import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { NgbModal, NgbModalConfig } from '@ng-bootstrap/ng-bootstrap';
import { Archivosdigitales } from 'src/app/_modelo/archivosdigitales';
import { ArchivosdigitalesService } from 'src/app/_servicio/archivosdigitales.service';
import { environment } from 'src/environments/environment';
import swal from 'sweetalert2';

@Component({
  selector: 'app-archivosdigitales',
  templateUrl: './archivosdigitales.component.html',
  styles: [
  ]
})
export class ArchivosdigitalesComponent implements OnInit {

  entidad: string = 'Archivosdigitales'
  titulo: string = 'Archivosdigitales';
  descripcion: string = 'Archivosdigitales';
  opcion: string = 'archivosdigitales';
  icono: string = 'list';

  rutaImagen = `${environment.RUTA}/api/archivos/descargarArchivo/`;
  archivoSeleccionado: File;

  datos: Archivosdigitales[];
  dato: Archivosdigitales;

  idproyecto: number;

  estado: string = '';

  formulario: FormGroup;
  submitted: boolean = false;

  constructor(
    private _ruta: Router,
    private _route: ActivatedRoute,
    private _archivosdigitalesService: ArchivosdigitalesService,
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
  }

  fdatos(id: number) {
    this._archivosdigitalesService.buscarTodos(id).subscribe((data) => {
      this.datos = data;
    });
  }

  fformulario(dato: Archivosdigitales) {
    this.formulario = this._fb.group({
      descripcion: [dato.descripcion, [Validators.required]]
    });
  }

  get f() {
    return this.formulario.controls;
  }

  fadicionar(content: any) {
    this.estado = 'Adicionar';
    this.dato = new Archivosdigitales();
    this.fformulario(this.dato);
    this._modalService.open(content);
  }

  fmodificar(id: number, content: any) {
    this.estado = 'Modificar';
    this._archivosdigitalesService.buscarPorId(id).subscribe((data) => {
      this.dato = data;
      this.fformulario(this.dato);
      this._modalService.open(content);
    });
  }

  faceptar(): void {
    this.submitted = true;
    this.dato.idproyecto = this.idproyecto;
    this.dato.descripcion = this.formulario.value.descripcion.toUpperCase();
    if (this.estado === 'Modificar') {
      this._archivosdigitalesService.modificar(this.dato).subscribe(
        (data) => {
          this.fsubirArchivo(this.dato.idarchivodigital);
          this.fdatos(this.idproyecto);
          this._modalService.dismissAll();
          swal.fire('Dato modificado', 'Dato modificado con exito', 'success');
        },
        (error) => {
          swal.fire('Error', 'Dato duplicado', 'error');
        }
      );
    } else {
      this._archivosdigitalesService.adicionar(this.dato).subscribe(
        (data) => {
          this._modalService.dismissAll();
          this.fsubirArchivo(data);
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
          this._archivosdigitalesService.borrar(id).subscribe((data) => {
            this.fdatos(this.idproyecto);
          });
        }
      });
  }

  fcancelar() {
    this._modalService.dismissAll();
  }

  fseleccionarArchivo(event) {
    const reader = new FileReader();
    this.archivoSeleccionado = event.target.files[0];
  }

  fsubirArchivo(id: number) {
    this._archivosdigitalesService.subirArchivo(id, this.archivoSeleccionado).subscribe( data => {
    })
  }


}
