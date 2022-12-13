import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { catchError, EMPTY, Observable, tap } from 'rxjs';
import { TransactionService } from 'src/app/services/transaction.service';

@Component({
  selector: 'app-bank',
  templateUrl: './bank.component.html',
  styleUrls: ['./bank.component.css']
})
export class BankComponent implements OnInit {

  newBankForm!: FormGroup;
  error?: string;
  type?: string;
  title?: string;
  feeAlert?:string;

  constructor(private router:Router,
    private formBuilder: FormBuilder,
    private transactionService: TransactionService,
    private route: ActivatedRoute) { }

  ngOnInit() {

    this.type = this.route.snapshot.paramMap.get('type')?.toString();

    if (this.type === "credit") {
      this.title = "Créditez mon compte Pay My Buddy";
      this.feeAlert = "(i) Une commission de 0,5% sera retenue sur cette transaction."
    } else {
      this.title = "Transférer vers mon compte bancaire";
      this.feeAlert = "(i) Une commission de 0,5% sera appliquée lors du virement vers votre banque."
    }
    this.initform();
  }

  private initform() {
    this.newBankForm = this.formBuilder.group({
      amount: [null, Validators.required],
    }, {
      updateOn: 'change'
    }
    );
  }

  onSubmitForm() {
    this.error = "";

    let obs$!: Observable<Object>;

    if (this.type === "credit") {
      obs$ = this.transactionService.receiveFromBank(this.newBankForm.value.amount);
    }
    else if (this.type === "payment") {
      obs$ = this.transactionService.sendToBank(this.newBankForm.value.amount);
    }
    else {
      return;
    }

    obs$.pipe(
      tap(() => this.router.navigateByUrl('home')),
      catchError((err) => {
        console.error(err.error);
        this.error = err.error;
        return EMPTY;
      })
      ).subscribe();
  }
}
