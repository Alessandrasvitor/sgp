import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { User } from 'src/app/shared/class/classes';
import { ErrorService } from 'src/app/shared/service/error.service';
import { SecurityService } from '../security.service';

@Component({
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {

  password: string | undefined;
  user: User = new User();

  constructor(
    private securityService: SecurityService,
    private errorService: ErrorService,
    private router: Router
  ) { }

  ngOnInit() { }

  login() {
    let newPassword = false;
    if(this.user.password === "123456") {
      newPassword = true;
    }

    this.securityService.login(this.user)
      .then((response: any) => {
        if(newPassword) {
          this.resetPws(response.user.email);
        } else if(response.user.flActive) {
					this.securityService.saveLocalStorege(response);
          this.router.navigate(['/'+response.user.startView]);
				} else {
          this.activeUser(response.user);
        }
      })
      .catch(erro => {
        this.errorService.handle(erro);
      });
  }

  register() {
    this.router.navigate(['/register']);
  }

  activeUser(user: any) {
    localStorage.setItem('user', JSON.stringify(user));
    this.router.navigate(['/active-user']);  
  }

  resetPws(email: any) {
    localStorage.setItem('email', email);
    this.router.navigate(['/reset-pwd']);
  }

}
