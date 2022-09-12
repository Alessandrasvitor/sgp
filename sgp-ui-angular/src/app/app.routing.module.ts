import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { UserComponent } from './components/user/user.component';
import { LoginComponent } from './security/login/login.component';
import { PageNotFoundComponent } from './security/page-not-found/page-not-found.component';
import { ResetPwdComponent } from './security/reset-pwd/reset-pwd.component';
import { SecurityGuard } from './security/security.guard';

const routes: Routes = [
  { path: '', redirectTo: 'redirect', pathMatch: 'full' },
  { path: 'page-not-found', component: PageNotFoundComponent },
  { path: 'reset-pwd', component: ResetPwdComponent },
  { path: 'login', component: LoginComponent },
  { path: 'user', component: UserComponent, canActivate: [SecurityGuard] },
  { path: '**', redirectTo: 'login' },
];

@NgModule({
  imports: [
    RouterModule.forRoot(routes)
  ],
  exports: [RouterModule]
})
export class AppRoutingModule { }