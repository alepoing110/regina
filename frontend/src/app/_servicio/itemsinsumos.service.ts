import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { environment } from 'src/environments/environment';
import { Itemsinsumos } from '../_modelo/itemsinsumos';

@Injectable({
  providedIn: 'root'
})
export class ItemsinsumosService {

  ruta = `${environment.RUTA}/api/v1/itemsinsumos`;

  constructor(private _httpClient: HttpClient) { }

  buscarTodos(iditem: number): Observable<Itemsinsumos[]> {
    return this._httpClient.get<Itemsinsumos[]>(`${this.ruta}/todos?iditem=${iditem}`);
  }

  buscarPorId(id: number): Observable<Itemsinsumos>{
    return this._httpClient.get<Itemsinsumos>(`${this.ruta}/${id}`);
  }

  adicionar(dato: Itemsinsumos): Observable<any>{
    return this._httpClient.post<any>(`${this.ruta}`, dato);
  }

  modificar(dato: Itemsinsumos): Observable<any>{
    return this._httpClient.put<any>(`${this.ruta}`, dato);
  }

  borrar(id: number): Observable<any>{
    return this._httpClient.delete<any>(`${this.ruta}/${id}`);
  }

}
