import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { environment } from 'src/environments/environment';
import { Actividadesitems } from '../_modelo/actividadesitems';

@Injectable({
  providedIn: 'root'
})
export class ActividadesitemsService {

  ruta = `${environment.RUTA}/api/v1/actividadesitems`;

  constructor(private _httpClient: HttpClient) { }

  buscarTodos(iditem: number): Observable<Actividadesitems[]> {
    return this._httpClient.get<Actividadesitems[]>(`${this.ruta}/todos?iditem=${iditem}`);
  }

  buscarPorId(id: number): Observable<Actividadesitems>{
    return this._httpClient.get<Actividadesitems>(`${this.ruta}/${id}`);
  }

  adicionar(dato: Actividadesitems): Observable<any>{
    return this._httpClient.post<any>(`${this.ruta}`, dato);
  }

  modificar(dato: Actividadesitems): Observable<any>{
    return this._httpClient.put<any>(`${this.ruta}`, dato);
  }

  borrar(id: number): Observable<any>{
    return this._httpClient.delete<any>(`${this.ruta}/${id}`);
  }

}
