import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { environment } from 'src/environments/environment';
import { Empleados } from '../_modelo/empleados';

@Injectable({
  providedIn: 'root'
})
export class EmpleadosService {

  ruta = `${environment.RUTA}/api/v1/empleados`;

  constructor(private _httpClient: HttpClient) {}

  buscarPorTermino(termino: string, pagina: number, cantidad: number): Observable<Empleados[]> {
    return this._httpClient.get<Empleados[]>(`${this.ruta}/?pagina=${pagina}&cantidad=${cantidad}&termino=${termino}`);
  }

  cantidadBuscarPorTermino(termino: string): Observable<number> {
    return this._httpClient.get<number>(`${this.ruta}/cantidad?termino=${termino}`);
  }

  buscarTodos(): Observable<Empleados[]> {
    return this._httpClient.get<Empleados[]>(`${this.ruta}/todos`);
  }

  buscarPorId(id: number): Observable<Empleados>{
    return this._httpClient.get<Empleados>(`${this.ruta}/${id}`);
  }

  adicionar(dato: Empleados): Observable<any>{
    return this._httpClient.post<any>(`${this.ruta}`, dato);
  }

  modificar(dato: Empleados): Observable<any>{
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
