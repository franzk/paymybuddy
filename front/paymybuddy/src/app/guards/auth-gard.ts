import { Injectable } from "@angular/core";
import { ActivatedRouteSnapshot, CanActivate, Router, RouterStateSnapshot } from "@angular/router";
import { LoginService } from "../services/login.service";

@Injectable({
  providedIn: 'root'
})
export class AuthGard implements CanActivate {

  constructor(private service: LoginService, private router: Router) {}

  canActivate(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): boolean {

      if (this.service.getAuthToken()) {
          return true;
      }
      else {
          this.router.navigateByUrl('login');
          return false;
      }
  }
}
