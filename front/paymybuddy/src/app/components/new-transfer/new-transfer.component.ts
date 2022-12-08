import { catchError } from 'rxjs/operators';
import { TransactionService } from 'src/app/services/transaction.service';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { User } from 'src/app/models/user.model';
import { tap, EMPTY, Observable } from 'rxjs';
import { FriendService } from 'src/app/services/friend.service';

@Component({
  selector: 'app-new-transfer',
  templateUrl: './new-transfer.component.html',
  styleUrls: ['./new-transfer.component.css']
})
export class NewTransferComponent implements OnInit {

  newTransferForm!: FormGroup;
  error?: string;
  friends$!: Observable<User[]>;
  selectedFriendEmail?: string;

  constructor(private router:Router,
              private formBuilder: FormBuilder,
              private transactionService: TransactionService,
              private friendService: FriendService) { }

  ngOnInit() {
    this.initform();
    this.friends$ = this.friendService.getFriends();
  }

  private initform() {
    this.newTransferForm = this.formBuilder.group({
      amount: [null, Validators.required],
      friends: ['default', Validators.required]
    }, {
      updateOn: 'change'
    }
    );
    this.selectedFriendEmail = "default";
  }

  onSubmitForm() {
    this.error = "";
    this.transactionService.newTransfer(this.newTransferForm.value.friends, this.newTransferForm.value.amount).pipe(
      tap(() => window.location.reload()),
      catchError((err) => {
        console.error(err.error);
        this.error = err.error;
        return EMPTY;
      })
    ).subscribe();
  }

}
