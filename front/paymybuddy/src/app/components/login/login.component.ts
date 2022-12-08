import { LoginService } from '../../services/login.service';
import { Component, OnInit } from '@angular/core';
import { catchError, EMPTY, tap } from 'rxjs';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {

  username?:string;
  password?:string;
  error?: string;

  constructor(private loginService:LoginService) { }

  ngOnInit(): void {}

  login() {
    this.error = "";
    this.loginService.login(this.username!, this.password!).pipe(
      tap(() =>  {
        this.loginService.loginOK(this.username!, this.password!);
      }),
      catchError((error) => {
        console.log(error);
        if (error.status == 401) {
          this.error = "Identifiants incorrects. Veuillez recommencer.";
          return EMPTY;
        } else {
          return error;
        }
      })
      ).subscribe();
  }

}
