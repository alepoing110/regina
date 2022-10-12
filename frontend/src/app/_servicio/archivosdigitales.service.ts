import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { environment } from 'src/environments/environment';
import { Archivosdigitales } from '../_modelo/archivosdigitales';

@Injectable({
  providedIn: 'root'
})
export class ArchivosdigitalesService {

  ruta = `${environment.RUTA}/api/v1/archivosdigitales`;

  constructor(private _httpClient: HttpClient) { }

  buscarTodos(idproyecto: number): Observable<Archivosdigitales[]> {
    return this._httpClient.get<Archivosdigitales[]>(`${this.ruta}/todos?idproyecto=${idproyecto}`);
  }

  buscarPorId(id: number): Observable<Archivosdigitales>{
    return this._httpClient.get<Archivosdigitales>(`${this.ruta}/${id}`);
  }

  adicionar(dato: Archivosdigitales): Observable<any>{
    return this._httpClient.post<any>(`${this.ruta}`, dato);
  }

  modificar(dato: Archivosdigitales): Observable<any>{
    return this._httpClient.put<any>(`${this.ruta}`, dato);
  }

  borrar(id: number): Observable<any>{
    return this._httpClient.delete<any>(`${this.ruta}/${id}`);
  }

  subirArchivo(id: number, archivo: File): Observable<any>{
    const formArchivo = new FormData();
    formArchivo.append("archivo", archivo);
    return this._httpClient.post<void>(`${this.ruta}/cargarArchivo/${id}`, formArchivo, {
      reportProgress: true,
      responseType: 'json',
    });
  }


  generaPDF(idproyecto: number) {
    return this._httpClient.get(`${this.ruta}/generaPDF/?idproyecto=${idproyecto}`, {
      responseType: "blob",
    });
  }

  generaXLS(idproyecto: number) {
    return this._httpClient.get(`${this.ruta}/generaXLS/?idproyecto=${idproyecto}`, {
      responseType: "blob",
    });
  }
}
