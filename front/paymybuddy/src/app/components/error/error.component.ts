import { ErrorService } from './../../services/error.service';
import { ActivatedRoute } from '@angular/router';
import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-error',
  templateUrl: './error.component.html',
  styleUrls: ['./error.component.css']
})
export class ErrorComponent implements OnInit {

  errorCode?: number;
  message?: string;

  constructor(private route: ActivatedRoute, private errorService: ErrorService) { }

  ngOnInit() {
    this.errorCode = +this.route.snapshot.params['code'];
    this.message = this.errorService.errorMessage(this.errorCode);
  }

}
