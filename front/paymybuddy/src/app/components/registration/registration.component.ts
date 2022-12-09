import { catchError } from 'rxjs/operators';
import { NewUserDto } from '../../models/new-user-dto.model';
import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { tap, EMPTY } from 'rxjs';
import { RegistrationService } from 'src/app/services/registration.service';

@Component({
  selector: 'app-registration',
  templateUrl: './registration.component.html',
  styleUrls: ['./registration.component.css']
})
export class RegistrationComponent implements OnInit {

  email?: string;
  name?: string;
  password?: string;
  emailRegExp?: RegExp;
  displayForm: boolean = true;
  registerOK: boolean = false;
  loading: boolean = false;
  error?: string;

  registrationForm!: FormGroup;

  constructor(private router:Router,
              private formBuilder: FormBuilder,
              private registrationService: RegistrationService) { }

  ngOnInit() {
    this.initform();
  }

  private initform() {
    this.emailRegExp = /^[\w-\.]+@([\w-]+\.)+[\w-]{2,4}$/;

    this.registrationForm = this.formBuilder.group({
      email: [null, [Validators.required, Validators.pattern(this.emailRegExp)]],
      name: [null, Validators.required],
      password: [null, Validators.required],
    }, {
      updateOn: 'change'
    }
    );
  }

  register() {
    this.loading = true;
    this.error = "";

    const newUser: NewUserDto = {
      ...this.registrationForm.value
    }
    this.registrationService.register(newUser).pipe(
      tap(() => {
          console.log('register tap');
          this.loading = false;
          this.registerOK = true;
          this.displayForm = false;
        }),
        catchError(err => {
          console.log('error register');
          this.error = err.error;
          return EMPTY;
        })
    ).subscribe();
  }
}
