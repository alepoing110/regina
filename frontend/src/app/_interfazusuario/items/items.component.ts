import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { NgbModal, NgbModalConfig } from '@ng-bootstrap/ng-bootstrap';
import { Items } from 'src/app/_modelo/items';
import { Unidades } from 'src/app/_modelo/unidades';
import { ItemsService } from 'src/app/_servicio/items.service';
import { UnidadesService } from 'src/app/_servicio/unidades.service';
import swal from 'sweetalert2';

@Component({
  selector: 'app-items',
  templateUrl: './items.component.html',
  styles: [
  ]
})
export class ItemsComponent implements OnInit {

  entidad: string = 'Items'
  titulo: string = 'Items';
  descripcion: string = 'Items';
  opcion: string = 'items';
  icono: string = 'list';

  datos_unidades: Unidades[];
  datos: Items[];
  dato: Items;

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
    private _itemsService: ItemsService,
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
    this.fdatos_unidades();
    this.fdatos();
  }

  fcantidad() {
    this._itemsService.cantidadBuscarPorTermino(this.buscar).subscribe((data) => {
      this.total = data;
    });
  }

  fbuscar(buscar: string) {
    this.buscar = buscar;
    this.pagina = 0;
    this.fdatos();
  }

  fdatos_unidades() {
    this._unidadesService.buscarTodos().subscribe((data) => {
      this.datos_unidades = data;
    });
  }

  fdatos() {
    this._itemsService.buscarPorTermino(this.buscar, this.pagina, this.cantidad).subscribe((data) => {
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

  fformulario(dato: Items) {
    this.formulario = this._fb.group({
      idunidad: [dato.idunidad, [Validators.required]],
      item: [dato.item, [Validators.required]],
      costounitario: [dato.costounitario, [Validators.required]]
    });
  }

  get f() {
    return this.formulario.controls;
  }

  fadicionar(content: any) {
    this.estado = 'Adicionar';
    this.dato = new Items();
    this.dato.costounitario=0;
    this.fformulario(this.dato);
    this._modalService.open(content);
  }

  fmodificar(id: number, content: any) {
    this.estado = 'Modificar';
    this._itemsService.buscarPorId(id).subscribe((data) => {
      this.dato = data;
      this.fformulario(this.dato);
      this._modalService.open(content);
    });
  }

  faceptar(): void {
    this.submitted = true;
    this.dato.idunidad = this.formulario.value.idunidad;
    this.dato.item = this.formulario.value.item.toUpperCase();
    this.dato.costounitario = this.formulario.value.costounitario;
    if (this.estado === 'Modificar') {
      this._itemsService.modificar(this.dato).subscribe(
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
      this._itemsService.adicionar(this.dato).subscribe(
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
          this._itemsService.borrar(id).subscribe((data) => {
            this.fdatos();
          });
        }
      });
  }

  finsumos(id: number) {
    const _ruta = '/items/' + id;
    this._ruta.navigateByUrl(_ruta);
  }

  fcancelar() {
    this._modalService.dismissAll();
  }

  fgeneraPDF() {
    this._itemsService.generaPDF().subscribe( data=> {
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
    this._itemsService.generaXLS().subscribe( data=> {
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
