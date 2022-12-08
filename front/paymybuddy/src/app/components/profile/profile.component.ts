import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup } from '@angular/forms';
import { Router } from '@angular/router';
import { Observable, tap } from 'rxjs';
import { User } from 'src/app/models/user.model';
import { UserService } from 'src/app/services/user.service';

@Component({
  selector: 'app-profile',
  templateUrl: './profile.component.html',
  styleUrls: ['./profile.component.css']
})
export class ProfileComponent implements OnInit {

  user$! : Observable<User>;
  userForm!: FormGroup;
  userName?: string;
  email?: string;

  constructor(private userService: UserService,
    private formBuilder: FormBuilder,
    private router: Router) { }

    ngOnInit() {
      this.userForm = this.formBuilder.group({
        name: [],
      });
      this.user$ = this.userService.getUser();
      this.user$.subscribe((user) => {
        this.userName = user.name;
        this.email = user.email;
      });
    }

    onSubmitForm() {
      this.userService.changeUserName(this.userName!).pipe(
        tap(() => {
          this.router.navigateByUrl("home");
        })
      ).subscribe();
    }
  }
