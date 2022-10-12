import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { environment } from 'src/environments/environment';
import { Roles } from '../_modelo/roles';

@Injectable({
  providedIn: 'root'
})
export class RolesService {

  ruta = `${environment.RUTA}/api/v1/roles`;

  constructor(private _httpClient: HttpClient) {}

  buscarTodos(): Observable<Roles[]> {
    return this._httpClient.get<Roles[]>(`${this.ruta}/todos`);
  }
}
