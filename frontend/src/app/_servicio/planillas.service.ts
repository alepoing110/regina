import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { environment } from 'src/environments/environment';
import { Planillas } from '../_modelo/planillas';

@Injectable({
  providedIn: 'root'
})
export class PlanillasService {

  ruta = `${environment.RUTA}/api/v1/planillas`;

  constructor(private _httpClient: HttpClient) { }

  buscarPorTermino(termino: string, pagina: number, cantidad: number): Observable<Planillas[]> {
    return this._httpClient.get<Planillas[]>(`${this.ruta}/?pagina=${pagina}&cantidad=${cantidad}&termino=${termino}`);
  }

  cantidadBuscarPorTermino(termino: string): Observable<number> {
    return this._httpClient.get<number>(`${this.ruta}/cantidad?termino=${termino}`);
  }

  buscarTodos(): Observable<Planillas[]> {
    return this._httpClient.get<Planillas[]>(`${this.ruta}/todos`);
  }

  buscarPorId(id: number): Observable<Planillas>{
    return this._httpClient.get<Planillas>(`${this.ruta}/${id}`);
  }

  adicionar(dato: Planillas): Observable<any>{
    return this._httpClient.post<any>(`${this.ruta}`, dato);
  }

  modificar(dato: Planillas): Observable<any>{
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
