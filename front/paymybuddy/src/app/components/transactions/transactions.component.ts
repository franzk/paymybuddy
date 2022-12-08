import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { Observable } from 'rxjs';
import { Transaction } from 'src/app/models/transaction.model';
import { TransactionsDto } from 'src/app/models/transactions-dto.model';
import { TransactionService } from 'src/app/services/transaction.service';

@Component({
  selector: 'app-transactions',
  templateUrl: './transactions.component.html',
  styleUrls: ['./transactions.component.css']
})
export class TransactionsComponent implements OnInit {

  transactions$!: Observable<TransactionsDto>;

  constructor(private transactionService: TransactionService,
              private router:Router) { }

  ngOnInit() {
    this.transactions$ = this.transactionService.getTransactions(0);
    this.transactions$.subscribe(console.log);
  }

  handlePageChange(event: any) {
    console.log(event);
    this.transactions$ = this.transactionService.getTransactions(event-1);
    this.transactions$.subscribe(console.log);
  }

  newTransfer() {
    this.router.navigateByUrl('newtransfer');
  }

  friend(transaction: Transaction): string {
    if (transaction.type === 'BANK') {
      if (transaction.amount! >= 0) {
        return 'Virement de ma banque';
      }
      else {
        return 'Virement vers ma banque';
      }
    }
    else {
      if (transaction.amount! > 0) {
        return ('De ' + transaction.senderName+' ('+transaction.senderEmail+')');
      }
      else if (transaction.amount! < 0) {
        return ('Vers ' + transaction.recipientName+' ('+transaction.recipientEmail+')');
      }
      else {
        return '';
      }
    }
  }


}
