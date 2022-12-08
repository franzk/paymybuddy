import { catchError } from 'rxjs/operators';
import { Router } from '@angular/router';
import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { FriendService } from 'src/app/services/friend.service';
import { User } from 'src/app/models/user.model';
import { tap, EMPTY } from 'rxjs';

@Component({
  selector: 'app-new-friend',
  templateUrl: './new-friend.component.html',
  styleUrls: ['./new-friend.component.css']
})
export class NewFriendComponent implements OnInit {

  newFriend?: User;
  newFriendForm!: FormGroup;
  emailRegExp?: RegExp;
  error?: string;

  constructor(private formBuilder: FormBuilder,
              private friendService: FriendService,
              private router:Router) { }

  ngOnInit(): void {
    this.emailRegExp = /^[\w-\.]+@([\w-]+\.)+[\w-]{2,4}$/;

    this.newFriendForm = this.formBuilder.group({
      email: [null, [Validators.required, Validators.pattern(this.emailRegExp) ]],
    }, {
      updateOn: 'change'
    });
  }

  onSubmitForm(): void {
    this.error = "";
    this.friendService.addFriend(this.newFriendForm.value.email).pipe(
      tap(() => this.router.navigateByUrl('transfer')),
      catchError(err => {
        console.log(err.error);
        this.error = err.error;
        return EMPTY;
    })
    ).subscribe();
  }
}
