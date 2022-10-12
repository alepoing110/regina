import { environment } from '../../environments/environment';
import { Injectable } from '@angular/core';
import swal from 'sweetalert2';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable, throwError } from 'rxjs';
import { Parametros } from '../_modelo/parametros';
import { catchError } from 'rxjs/operators';

@Injectable({
  providedIn: 'root',
})
export class ParametrosService {
  ruta = `${environment.RUTA}/api/v1/parametros`;

  constructor(private _httpClient: HttpClient) {}

  datos(
    pagina: number,
    cantidad: number,
    buscar: string
  ): Observable<Parametros[]> {
    return this._httpClient.get<Parametros[]>(
      `${this.ruta}/?pagina=${pagina}&cantidad=${cantidad}&buscar=${buscar}`);
  }

  cantidad(buscar: string): Observable<number> {
    return this._httpClient.get<number>(
      `${this.ruta}/cantidad?buscar=${buscar}`);
  }

  dato(id: number): Observable<Parametros> {
    return this._httpClient.get<Parametros>(`${this.ruta}/${id}`);
  }

  modificar(dato: Parametros): Observable<any> {
    return this._httpClient
      .put<void>(`${this.ruta}`, dato)
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

  parametroi(parametro: string): Observable<number> {
    return this._httpClient.get<number>(
      `${this.ruta}/parametroI?parametro=${parametro}`);
  }
}
