import { LoginService } from './../services/login.service';
import { Router } from '@angular/router';
import { HttpEvent, HttpHandler, HttpRequest, HttpErrorResponse, HttpInterceptor} from '@angular/common/http';
import { Injectable } from '@angular/core';
import { EMPTY, Observable } from 'rxjs';
import { catchError } from 'rxjs/operators';


@Injectable()
export class ErrorInterceptor implements HttpInterceptor {

  constructor(private router: Router, private loginService: LoginService) {}

  intercept(request: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {

    return next.handle(request).pipe(
      catchError((error: HttpErrorResponse) => {

        if ((request.url === 'http://localhost:8080/perform_login') && (error.status == 401)) {
          return next.handle(request);
        }

        if ([0, 401, 500].includes(error.status)) {
          this.router.navigateByUrl('error/' + error.status);
          return EMPTY;
        }
        else {
          return next.handle(request);
        }

      })
    );
  }
}
