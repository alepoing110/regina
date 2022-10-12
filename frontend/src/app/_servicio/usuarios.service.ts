import { environment } from '../../environments/environment';
import { Usuarios } from '../_modelo/usuarios';
import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable, throwError } from 'rxjs';
import { catchError } from 'rxjs/operators';
import swal from 'sweetalert2';

@Injectable({
  providedIn: 'root',
})
export class UsuariosService {

  ruta = `${environment.RUTA}/api/v1/usuarios`;

  constructor(private _httpClient: HttpClient) {}

  cambiarclave(dato: Usuarios): Observable<any> {
    return this._httpClient
      .put<void>(`${this.ruta}/cambiarclave`, dato)
      .pipe(
        catchError((e) => {
          if (e.status === 400) {
            return throwError(e);
          }
          swal.fire(e.error.mensaje, e.error.error, 'error');
          return throwError(e);
        })
      );
  }

  buscarPorTermino(termino: string, pagina: number, cantidad: number): Observable<Usuarios[]> {
    return this._httpClient.get<Usuarios[]>(`${this.ruta}/?pagina=${pagina}&cantidad=${cantidad}&termino=${termino}`);
  }

  cantidadBuscarPorTermino(termino: string): Observable<number> {
    return this._httpClient.get<number>(`${this.ruta}/cantidad?termino=${termino}`);
  }

  buscarTodos(): Observable<Usuarios[]> {
    return this._httpClient.get<Usuarios[]>(`${this.ruta}/todos`);
  }

  buscarPorId(id: number): Observable<Usuarios>{
    return this._httpClient.get<Usuarios>(`${this.ruta}/${id}`);
  }

  adicionar(dato: Usuarios): Observable<any>{
    return this._httpClient.post<any>(`${this.ruta}`, dato);
  }

  modificar(dato: Usuarios): Observable<any>{
    return this._httpClient.put<any>(`${this.ruta}`, dato);
  }

  borrar(id: number): Observable<any>{
    return this._httpClient.delete<any>(`${this.ruta}/${id}`);
  }

  generaPDF() {
    return this._httpClient.get(`${this.ruta}/generaPDF/`, {
      responseType: "blob",
    });
  }

  generaXLS() {
    return this._httpClient.get(`${this.ruta}/generaXLS/`, {
      responseType: "blob",
    });
  }

}
