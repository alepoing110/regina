import { environment } from '../../environments/environment';
import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Menus } from '../_modelo/menus';

@Injectable({
  providedIn: 'root',
})
export class MenusService {
  ruta = `${environment.RUTA}/api/v1/menus`;

  constructor(private _httpClient: HttpClient) {}

  datos(): Observable<Menus[]> {
    return this._httpClient.get<Menus[]>(`${this.ruta}`);
  }
}
