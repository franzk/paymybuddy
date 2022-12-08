import { BankComponent } from './components/bank/bank.component';
import { ErrorComponent } from './components/error/error.component';
import { RegistrationComponent } from './components/registration/registration.component';
import { NewTransferComponent } from './components/new-transfer/new-transfer.component';
import { NewFriendComponent } from './components/new-friend/new-friend.component';
import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { HomeComponent } from './components/home/home.component';
import { LoginComponent } from './components/login/login.component';
import { AuthGard } from './guards/auth-gard';
import { Error404Component } from './components/error404/error404.component';
import { ProfileComponent } from './components/profile/profile.component';


const routes: Routes = [
  {path:"", redirectTo:"login", pathMatch:"full"},
  {path:'login', component:LoginComponent},
  {path:'home', component:HomeComponent, canActivate: [AuthGard]},
  {path:'newfriend', component:NewFriendComponent, canActivate: [AuthGard]},
  {path:'transfer', component:NewTransferComponent, canActivate: [AuthGard]},
  {path:'bank/:type', component:BankComponent, canActivate: [AuthGard]},
  {path:'profile', component:ProfileComponent, canActivate: [AuthGard]},
  {path:'register', component:RegistrationComponent},
  { path: 'error/:code', component: ErrorComponent },
  { path: '**', pathMatch: 'full', component: Error404Component },
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
