import { Router } from '@angular/router';
import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css']
})
export class HomeComponent implements OnInit {

  constructor(private router:Router) { }

  ngOnInit(): void {
  }

  transfer() {
    this.router.navigateByUrl("transfer");
  }

  credit() {
    this.router.navigateByUrl('bank/credit');
  }

  payment() {
    this.router.navigateByUrl('bank/payment');
  }

}
