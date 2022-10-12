import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { environment } from 'src/environments/environment';
import { Insumos } from '../_modelo/insumos';

@Injectable({
  providedIn: 'root'
})
export class InsumosService {

  ruta = `${environment.RUTA}/api/v1/insumos`;

  constructor(private _httpClient: HttpClient) { }

  buscarPorTermino(termino: string, pagina: number, cantidad: number): Observable<Insumos[]> {
    return this._httpClient.get<Insumos[]>(`${this.ruta}/?pagina=${pagina}&cantidad=${cantidad}&termino=${termino}`);
  }

  cantidadBuscarPorTermino(termino: string): Observable<number> {
    return this._httpClient.get<number>(`${this.ruta}/cantidad?termino=${termino}`);
  }

  buscarTodos(): Observable<Insumos[]> {
    return this._httpClient.get<Insumos[]>(`${this.ruta}/todos`);
  }

  buscarTodosManoObra(): Observable<Insumos[]> {
    return this._httpClient.get<Insumos[]>(`${this.ruta}/todosManoObra`);
  }

  buscarPorId(id: number): Observable<Insumos>{
    return this._httpClient.get<Insumos>(`${this.ruta}/${id}`);
  }

  adicionar(dato: Insumos): Observable<any>{
    return this._httpClient.post<any>(`${this.ruta}`, dato);
  }

  modificar(dato: Insumos): Observable<any>{
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
