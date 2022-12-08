import { TransactionsDto } from './../models/transactions-dto.model';
import { HttpClient, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { NewTransactionDto } from '../models/new-transaction-dto.model';

@Injectable({
  providedIn: 'root'
})
export class TransactionService {

  constructor(private http:HttpClient) { }

  public getTransactions(page: number) {
    let queryParams = new HttpParams();
    queryParams = queryParams.append("page",page);
    return this.http.get<TransactionsDto>("http://localhost:8080/transactions", {params: queryParams});
  }

  public newTransfer(friendEmail: string, amount: number) {
    const dto: NewTransactionDto = {
      recipientEmail: friendEmail,
      amount: amount
    }
    return this.http.post("http://localhost:8080/transfer", dto);
  }

  public receiveFromBank(amount: number) {
    const dto: NewTransactionDto = {
      amount: amount
    }
    return this.http.post("http://localhost:8080/bank/from", dto);
  }

  public sendToBank(amount: number) {
    const dto: NewTransactionDto = {
      amount: amount
    }
    return this.http.post("http://localhost:8080/bank/to", dto);
  }

}
