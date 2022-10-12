import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { environment } from 'src/environments/environment';
import { Proyectos } from '../_modelo/proyectos';

@Injectable({
  providedIn: 'root'
})
export class ProyectosService {

  ruta = `${environment.RUTA}/api/v1/proyectos`;

  constructor(private _httpClient: HttpClient) { }

  buscarPorTermino(termino: string, pagina: number, cantidad: number): Observable<Proyectos[]> {
    return this._httpClient.get<Proyectos[]>(`${this.ruta}/?pagina=${pagina}&cantidad=${cantidad}&termino=${termino}`);
  }

  cantidadBuscarPorTermino(termino: string): Observable<number> {
    return this._httpClient.get<number>(`${this.ruta}/cantidad?termino=${termino}`);
  }

  buscarTodos(): Observable<Proyectos[]> {
    return this._httpClient.get<Proyectos[]>(`${this.ruta}/todos`);
  }

  buscarPorId(id: number): Observable<Proyectos>{
    return this._httpClient.get<Proyectos>(`${this.ruta}/${id}`);
  }

  adicionar(dato: Proyectos): Observable<any>{
    return this._httpClient.post<any>(`${this.ruta}`, dato);
  }

  modificar(dato: Proyectos): Observable<any>{
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
