import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { environment } from 'src/environments/environment';
import { Tiposextensiones } from '../_modelo/tiposextensiones';

@Injectable({
  providedIn: 'root'
})
export class TiposextensionesService {

  ruta = `${environment.RUTA}/api/v1/tiposextensiones`;

  constructor(private _httpClient: HttpClient) {}

  buscarTodos(): Observable<Tiposextensiones[]> {
    return this._httpClient.get<Tiposextensiones[]>(`${this.ruta}/todos`);
  }
}
