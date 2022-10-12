import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { environment } from 'src/environments/environment';
import { Proveedores } from '../_modelo/proveedores';

@Injectable({
  providedIn: 'root'
})
export class ProveedoresService {

  ruta = `${environment.RUTA}/api/v1/proveedores`;

  constructor(private _httpClient: HttpClient) { }

  buscarPorTermino(termino: string, pagina: number, cantidad: number): Observable<Proveedores[]> {
    return this._httpClient.get<Proveedores[]>(`${this.ruta}/?pagina=${pagina}&cantidad=${cantidad}&termino=${termino}`);
  }

  cantidadBuscarPorTermino(termino: string): Observable<number> {
    return this._httpClient.get<number>(`${this.ruta}/cantidad?termino=${termino}`);
  }

  buscarTodos(): Observable<Proveedores[]> {
    return this._httpClient.get<Proveedores[]>(`${this.ruta}/todos`);
  }

  buscarPorId(id: number): Observable<Proveedores>{
    return this._httpClient.get<Proveedores>(`${this.ruta}/${id}`);
  }

  adicionar(dato: Proveedores): Observable<any>{
    return this._httpClient.post<any>(`${this.ruta}`, dato);
  }

  modificar(dato: Proveedores): Observable<any>{
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
