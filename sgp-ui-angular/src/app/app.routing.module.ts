import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { CourseComponent } from './features/course/course.component';
import { InstitutionComponent } from './features/institution/institution.component';
import { UserComponent } from './features/user/user.component';
import { LoginComponent } from './security/login/login.component';
import { PageNotFoundComponent } from './security/page-not-found/page-not-found.component';
import { ResetPwdComponent } from './security/reset-pwd/reset-pwd.component';
import { SecurityGuard } from './security/security.guard';

const routes: Routes = [
  { path: '', redirectTo: 'redirect', pathMatch: 'full' },
  { path: 'page-not-found', component: PageNotFoundComponent },
  { path: 'reset-pwd', component: ResetPwdComponent },
  { path: 'login', component: LoginComponent },
  { path: 'users', component: UserComponent, canActivate: [SecurityGuard] },
  { path: 'courses', component: CourseComponent, canActivate: [SecurityGuard] },
  { path: 'instituitions', component: InstitutionComponent, canActivate: [SecurityGuard] },
  { path: '**', redirectTo: 'login' },
];

@NgModule({
  imports: [
    RouterModule.forRoot(routes)
  ],
  exports: [RouterModule]
})
export class AppRoutingModule { }