import { HttpClient, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Router } from '@angular/router';
import { Observable, Subject, tap } from 'rxjs';
import { User } from '../models/user.model';

@Injectable({
  providedIn: 'root'
})
export class FriendService {

  // configuration pour permettre le refresh automatique de la liste des friends après suppression d'un friend
  private _deleteOperationSuccessfulEvent$: Subject<boolean> = new Subject();
  get deleteOperationSuccessfulEvent$(): Observable<boolean> {
    return this._deleteOperationSuccessfulEvent$.asObservable();
  }

  constructor(private http: HttpClient, private router:Router) { }

  public getFriends() {
    return this.http.get<User[]>("http://localhost:8080/friends");
  }

  public addFriend(friendEmail: string) {
    const newFriend: User = {
      email: friendEmail
    }

    return this.http.post<User>("http://localhost:8080/friend", newFriend);
  }

  public removeFriend(email: string) {
    let queryParams = new HttpParams();
    queryParams = queryParams.append("email", email);

    this.http.delete("http://localhost:8080/friend", {params: queryParams}).pipe(
      tap(() => this._deleteOperationSuccessfulEvent$.next(true)) // refresh auto de la liste des friends après suppression
    )
    .subscribe();
  }

}
