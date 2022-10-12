import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { environment } from 'src/environments/environment';
import { Actividades } from '../_modelo/actividades';

@Injectable({
  providedIn: 'root'
})
export class ActividadesService {

  ruta = `${environment.RUTA}/api/v1/actividades`;

  constructor(private _httpClient: HttpClient) { }

  buscarTodos(iditem: number): Observable<Actividades[]> {
    return this._httpClient.get<Actividades[]>(`${this.ruta}/todos?iditem=${iditem}`);
  }

  buscarPorId(id: number): Observable<Actividades>{
    return this._httpClient.get<Actividades>(`${this.ruta}/${id}`);
  }

  adicionar(dato: Actividades): Observable<any>{
    return this._httpClient.post<any>(`${this.ruta}`, dato);
  }

  modificar(dato: Actividades): Observable<any>{
    return this._httpClient.put<any>(`${this.ruta}`, dato);
  }

  borrar(id: number): Observable<any>{
    return this._httpClient.delete<any>(`${this.ruta}/${id}`);
  }

}
