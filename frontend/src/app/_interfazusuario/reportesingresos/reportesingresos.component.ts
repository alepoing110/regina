import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { NgbModal, NgbModalConfig } from '@ng-bootstrap/ng-bootstrap';
import { Instituciones } from 'src/app/_modelo/instituciones';
import { Proyectos } from 'src/app/_modelo/proyectos';
import { InstitucionesService } from 'src/app/_servicio/instituciones.service';
import { ProyectosService } from 'src/app/_servicio/proyectos.service';
import swal from 'sweetalert2';

@Component({
  selector: 'app-reportesingresos',
  templateUrl: './reportesingresos.component.html',
  styles: [
  ]
})
export class ReportesingresosComponent implements OnInit {

  entidad: string = 'Proyectos'
  titulo: string = 'Proyectos';
  descripcion: string = 'Proyectos';
  opcion: string = 'proyectos';
  icono: string = 'list';

  datos_instituciones: Instituciones[];
  datos: Proyectos[];
  dato: Proyectos;

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
    private _proyectosService: ProyectosService,
    private _institucionesService: InstitucionesService,
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
    this.fdatos_instituciones();
    this.fdatos();
  }

  fcantidad() {
    this._proyectosService.cantidadBuscarPorTermino(this.buscar).subscribe((data) => {
      this.total = data;
    });
  }

  fbuscar(buscar: string) {
    this.buscar = buscar;
    this.pagina = 0;
    this.fdatos();
  }

  fdatos() {
    this._proyectosService.buscarPorTermino(this.buscar, this.pagina, this.cantidad).subscribe((data) => {
      this.fcantidad();
      this.datos = data;
    });
  }

  fdatos_instituciones() {
    this._institucionesService.buscarTodos().subscribe((data) => {
      this.datos_instituciones = data;
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

  fformulario(dato: Proyectos) {
    this.formulario = this._fb.group({
      idinstitucion: [dato.idinstitucion, [Validators.required]],
      proyecto: [dato.proyecto, [Validators.required]],
      detalle: [dato.detalle, [Validators.required]],
      costototal: [dato.costototal, [Validators.required]],
      costoejecutado: [dato.costoejecutado, [Validators.required]],
    });
  }

  get f() {
    return this.formulario.controls;
  }

  fadicionar(content: any) {
    this.estado = 'Adicionar';
    this.dato = new Proyectos();
    this.dato.costoejecutado=0;
    this.fformulario(this.dato);
    this._modalService.open(content);
  }

  fmodificar(id: number, content: any) {
    this.estado = 'Modificar';
    this._proyectosService.buscarPorId(id).subscribe((data) => {
      this.dato = data;
      this.fformulario(this.dato);
      this._modalService.open(content);
    });
  }

  faceptar(): void {
    this.submitted = true;
    this.dato.idinstitucion = this.formulario.value.idinstitucion;
    this.dato.proyecto = this.formulario.value.proyecto.toUpperCase();
    this.dato.detalle = this.formulario.value.detalle.toUpperCase();
    this.dato.costototal = this.formulario.value.costototal;
    this.dato.costoejecutado = this.formulario.value.costoejecutado;
    if (this.estado === 'Modificar') {
      this._proyectosService.modificar(this.dato).subscribe(
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
      this._proyectosService.adicionar(this.dato).subscribe(
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
          this._proyectosService.borrar(id).subscribe((data) => {
            this.fdatos();
          });
        }
      });
  }

  fcancelar() {
    this._modalService.dismissAll();
  }

  factividades(id: number){
    const _ruta = '/proyectos/' + id;
    this._ruta.navigateByUrl(_ruta);
  }

  fgeneraPDF() {
    this._proyectosService.generaPDF().subscribe( data=> {
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
    this._proyectosService.generaXLS().subscribe( data=> {
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

  farchivosdigitales(id: number){
    const _ruta = '/preparacion/' + id;
    this._ruta.navigateByUrl(_ruta);
  }

}
