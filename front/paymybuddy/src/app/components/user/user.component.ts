import { UserService } from './../../services/user.service';
import { Component, OnInit } from '@angular/core';
import { Observable } from 'rxjs';
import { User } from 'src/app/models/user.model';

@Component({
  selector: 'app-user',
  templateUrl: './user.component.html',
  styleUrls: ['./user.component.css']
})
export class UserComponent implements OnInit {

  user$! : Observable<User>;

  constructor(private userService: UserService) { }

  ngOnInit() {
    this.user$ = this.userService.getUser();
  }

}
