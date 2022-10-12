import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { environment } from 'src/environments/environment';
import { Tiposdocumentos } from '../_modelo/tiposdocumentos';

@Injectable({
  providedIn: 'root'
})
export class TiposdocumentosService {

  ruta = `${environment.RUTA}/api/v1/tiposdocumentos`;

  constructor(private _httpClient: HttpClient) {}

  buscarTodos(): Observable<Tiposdocumentos[]> {
    return this._httpClient.get<Tiposdocumentos[]>(`${this.ruta}/todos`);
  }
}
