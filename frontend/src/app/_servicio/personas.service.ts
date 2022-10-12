import { environment } from '../../environments/environment';
import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Personas } from '../_modelo/personas';
import { Personasl } from '../_modelo/personasl';

@Injectable({
  providedIn: 'root',
})
export class PersonasService {
  ruta = `${environment.RUTA}/api/v1/personas`;

  constructor(private _httpClient: HttpClient) {}

  perfil(): Observable<Personas> {
    return this._httpClient.get<Personas>(`${this.ruta}/perfil`);
  }

  cargarImagen(archivo: File): Observable<any> {
    const formData: FormData = new FormData();
    formData.append('archivo', archivo);
    return this._httpClient.post<void>(`${this.ruta}/cargar/`, formData, {
      reportProgress: true,
      responseType: 'json'
    });
  }

  descargarImagen() {
    return this._httpClient.get(`${this.ruta}/descargar/`, {
      responseType: 'blob'
    });
  }

  buscarPorTermino(termino: string, pagina: number, cantidad: number): Observable<Personas[]> {
    return this._httpClient.get<Personas[]>(`${this.ruta}/?pagina=${pagina}&cantidad=${cantidad}&termino=${termino}`);
  }

  cantidadBuscarPorTermino(termino: string): Observable<number> {
    return this._httpClient.get<number>(`${this.ruta}/cantidad?termino=${termino}`);
  }

  buscarTodos(): Observable<Personas[]> {
    return this._httpClient.get<Personas[]>(`${this.ruta}/todos`);
  }

  buscarPorId(id: number): Observable<Personas>{
    return this._httpClient.get<Personas>(`${this.ruta}/${id}`);
  }

  adicionar(dato: Personas): Observable<any>{
    return this._httpClient.post<any>(`${this.ruta}`, dato);
  }

  modificar(dato: Personas): Observable<any>{
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
