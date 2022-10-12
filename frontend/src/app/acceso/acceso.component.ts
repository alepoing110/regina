import { Component, OnInit } from '@angular/core';
import { FormGroup, FormBuilder, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { AccesoService } from '../_servicio/acceso.service';
import * as jwt_decode from 'jwt-decode';
import swal from 'sweetalert2';
import { ToastrService } from 'ngx-toastr';
import { environment } from 'src/environments/environment';

@Component({
  selector: 'app-acceso',
  templateUrl: './acceso.component.html',
  styleUrls: ['./acceso.component.css'],
})
export class AccesoComponent implements OnInit {

  ROLES: string = environment.ROLES;
  NOMBRE: string = environment.NOMBRE;
  TOKEN: string = environment.TOKEN;

  gestion: number = new Date().getFullYear();
  nombreapp: string = environment.nombreapp;
  version: string = environment.version;

  formulario: FormGroup;
  submitted = false;
  error = '';
  estado: boolean = false;

  constructor(
    private _fb: FormBuilder,
    private _ruta: Router,
    private _accesoService: AccesoService,
    private _mensajes: ToastrService
  ) {}

  ngOnInit() {
    this.crearformulario();
  }

  crearformulario() {
    this.formulario = this._fb.group({
      usuario: ['', Validators.required],
      clave: ['', Validators.required],
    });
  }

  get f() {
    return this.formulario.controls;
  }

  faceptar() {
    this.estado = true;
    this.submitted = true;
    this._accesoService
      .acceso(this.f.usuario.value, this.f.clave.value)
      .subscribe(
        (data) => {

          if (data) {
            const token = JSON.stringify(data);
            var decoded = jwt_decode(token);
            sessionStorage.setItem(this.ROLES, decoded['authorities']);
            sessionStorage.setItem(this.NOMBRE, decoded['nombre']);
            sessionStorage.setItem(this.TOKEN, token);
            this._mensajes.success(decoded['nombre'], 'Bienvenido');
            this._ruta.navigateByUrl('/escritorio');
            this.estado = false;
          }
        },
        (err) => {
          if (err.status === 0) {
            this.error = 'Error de conexión';
            swal.fire('Verifica tu conexión!', 'Sin conexión al sistema', 'error');
          }
          if (err.status === 400) {
            this.error = 'Credenciales incorrectas';
            swal.fire('Verifica tus datos!', 'Tu usuario ó contraseña son incorrectos', 'warning');
          }
          if (err.status === 401) {
            this.error = 'Credenciales incorrectas';
            swal.fire('Verifica tus datos!', 'Tu usuario ó contraseña son incorrectos', 'warning');
          }
          if (err.status === 500) {
            this.error = 'Error';
            swal.fire('Sistema vulnerado', 'Contactarse con el departamento de sistemas', 'error');
          }
          this.f.clave.setValue('');
          this.estado = false;
        }
      );
  }
}
