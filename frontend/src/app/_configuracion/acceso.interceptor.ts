import { Injectable } from '@angular/core';
import {
  HttpRequest,
  HttpHandler,
  HttpEvent,
  HttpInterceptor,
  HttpHeaders,
  HttpErrorResponse
} from '@angular/common/http';
import { Observable } from 'rxjs';
import { environment } from 'src/environments/environment';
import { Router } from '@angular/router';
import { tap } from 'rxjs/operators';

@Injectable()
export class AccesoInterceptor implements HttpInterceptor {

  TOKEN: string = environment.TOKEN;

  constructor(
    private router: Router
  ) {
  }

  intercept(req: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
    if (req.url.search('/oauth/token') === -1) {
      if (sessionStorage.getItem(this.TOKEN) !== null) {
        const access_token = JSON.parse(sessionStorage.getItem(this.TOKEN)).access_token;
        const reqheader = req.clone({
          headers: new HttpHeaders()
            .set('Authorization', `bearer ${access_token}`)
        });
        return next.handle(reqheader).pipe( tap(() => {},
        (err: any) => {
        if (err instanceof HttpErrorResponse) {
          if (err.status !== 401) {
           return;
          }
          this.router.navigate(['acceso']);
        }
      }));
      } else {
        return next.handle(req);
      }
    } else {
      return next.handle(req);
    }
  }

}
