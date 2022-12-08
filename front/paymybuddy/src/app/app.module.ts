import { ErrorComponent } from './components/error/error.component';
import { BankComponent } from './components/bank/bank.component';
import { HeaderComponent } from './components/header/header.component';
import { RegistrationService } from './services/registration.service';
import { TransactionService } from './services/transaction.service';
import { FriendService } from './services/friend.service';
import { UserComponent } from './components/user/user.component';
import { TransactionsComponent } from './components/transactions/transactions.component';
import { UserService } from './services/user.service';
import { NewFriendComponent } from './components/new-friend/new-friend.component';
import { LoginService } from './services/login.service';
import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { LoginComponent } from './components/login/login.component';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { HttpClientModule, HTTP_INTERCEPTORS } from '@angular/common/http';
import { HomeComponent } from './components/home/home.component';
import { AuthGard } from './guards/auth-gard';
import { AuthInterceptor } from './interceptors/auth-interceptor';
import { RegistrationComponent } from './components/registration/registration.component';
import { NgxPaginationModule } from 'ngx-pagination';
import { Error404Component } from './components/error404/error404.component';
import { ErrorInterceptor } from './interceptors/error-interceptor';
import { ProfileComponent } from './components/profile/profile.component';
import { NewTransferComponent } from './components/new-transfer/new-transfer.component';


@NgModule({
  declarations: [
    AppComponent,
    LoginComponent,
    HomeComponent,
    NewFriendComponent,
    TransactionsComponent,
    UserComponent,
    NewTransferComponent,
    RegistrationComponent,
    Error404Component,
    HeaderComponent,
    ProfileComponent,
    BankComponent,
    ErrorComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    FormsModule,
    ReactiveFormsModule,
    HttpClientModule,
    NgxPaginationModule,
  ],
  providers: [
    LoginService,
    UserService,
    FriendService,
    TransactionService,
    RegistrationService,
    { provide: HTTP_INTERCEPTORS, useClass: AuthInterceptor, multi: true },
    { provide: HTTP_INTERCEPTORS, useClass: ErrorInterceptor, multi: true },
    AuthGard
  ],
  bootstrap: [AppComponent],
})
export class AppModule { }
