import { LoginService } from './../services/login.service';
import { HttpEvent, HttpHandler, HttpInterceptor, HttpRequest } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { Observable } from "rxjs";

@Injectable()
export class AuthInterceptor implements HttpInterceptor {

  constructor(private loginService : LoginService) {
  }

  intercept(request: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {

    if (request.url !== 'http://localhost:8080/perform_login') {
      const modifiedReq  = request.clone({
        headers: request.headers.set('Authorization', this.loginService.getAuthToken())
      });
    return next.handle(modifiedReq);
    }
    else {
      return next.handle(request);
    }
  }
}

