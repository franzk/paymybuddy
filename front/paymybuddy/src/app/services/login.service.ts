import { Router } from '@angular/router';
import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { pipe, tap, Observable, of } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class LoginService {

  isLogged$!: Observable<boolean>;

  constructor(private http:HttpClient, private router: Router) { }

  public login(username: string, password: string) {

    return this.http.get<string>("http://localhost:8080/perform_login",
      { headers: {'Authorization': this.authToken(username, password)},
        responseType:'text' as 'json'
      });
  }

  loginOK(username: string, password: string) {
    this.setAuthToken(this.authToken(username, password));
    this.router.navigate(["/home"]);
  }

  public logout() {
    localStorage.removeItem('authToken');
    this.isLogged$ = of(false);
    this.router.navigateByUrl("/login");
    this.http.get("http://localhost:8080/perform_logout", {responseType:'text' as 'json'}).subscribe();

  }

  private setAuthToken(authToken: string) {
    localStorage.setItem('authToken', authToken);
    this.isLogged$ = of(true);
  }

  public getAuthToken() : string {
    let token = localStorage.getItem('authToken');

    if (token === null) {
      this.isLogged$ = of(false);
      return "";
    }
    else {
      this.isLogged$ = of(true);
      return (token);
    }
  }

  private authToken(username: string, password: string): string {
    return 'Basic ' + window.btoa(username + ":" + password);
  }

}
