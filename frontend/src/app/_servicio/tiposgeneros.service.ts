import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { environment } from 'src/environments/environment';
import { Tiposgeneros } from '../_modelo/tiposgeneros';

@Injectable({
  providedIn: 'root'
})
export class TiposgenerosService {

  ruta = `${environment.RUTA}/api/v1/tiposgeneros`;

  constructor(private _httpClient: HttpClient) {}

  buscarTodos(): Observable<Tiposgeneros[]> {
    return this._httpClient.get<Tiposgeneros[]>(`${this.ruta}/todos`);
  }
}
