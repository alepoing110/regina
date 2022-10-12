import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { environment } from 'src/environments/environment';
import { Planillasempleados } from '../_modelo/planillasempleados';

@Injectable({
  providedIn: 'root'
})
export class PlanillasempleadosService {

  ruta = `${environment.RUTA}/api/v1/planillasempleados`;

  constructor(private _httpClient: HttpClient) { }

  buscarTodos(iditem: number): Observable<Planillasempleados[]> {
    return this._httpClient.get<Planillasempleados[]>(`${this.ruta}/todos?iditem=${iditem}`);
  }

  buscarPorId(id: number): Observable<Planillasempleados>{
    return this._httpClient.get<Planillasempleados>(`${this.ruta}/${id}`);
  }

  adicionar(dato: Planillasempleados): Observable<any>{
    return this._httpClient.post<any>(`${this.ruta}`, dato);
  }

  modificar(dato: Planillasempleados): Observable<any>{
    return this._httpClient.put<any>(`${this.ruta}`, dato);
  }

  borrar(id: number): Observable<any>{
    return this._httpClient.delete<any>(`${this.ruta}/${id}`);
  }
}
