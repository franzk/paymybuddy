import { NewUserDto } from './../models/new-user-dto.model';
import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Transaction } from '../models/transaction.model';
import { User } from '../models/user.model';

@Injectable({
  providedIn: 'root'
})
export class UserService {

  constructor(private http: HttpClient) { }

  public getUser() {
    return this.http.get<User>("http://localhost:8080/user");
  }

  public changeUserName(userName : string) {
    const user: User = {
      email: '',
      name : userName,
      balance: 0
    }
    return this.http.put("http://localhost:8080/user", user);
  }
}
