import { NewUserDto } from './../models/new-user-dto.model';
import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class RegistrationService {

  constructor(private http:HttpClient) { }

  public register(newUser: NewUserDto) {
    return this.http.post('http://localhost:8080/register', newUser, { responseType : 'text' as 'json'});
  }
}
