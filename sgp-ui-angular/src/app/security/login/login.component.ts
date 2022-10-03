import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { MessageService } from 'primeng/api';
import { User } from 'src/app/shared/class/classes';
import { ErrorService } from 'src/app/shared/service/error.service';
import { SecurityService } from '../security.service';

@Component({
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {

  user: User = new User();
  createNewPwd = false;
  password: string | undefined;

  constructor(
    private securityService: SecurityService,
    private errorService: ErrorService,
    private router: Router,
    private messageService: MessageService
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
          this.user = response.user;
          this.cleanPassword();
          this.createNewPwd = true;
        } else {
          localStorage.setItem('userLogin', JSON.stringify(response.user));
          this.router.navigate(['/'+response.user.startView]);
        }
      })
      .catch(erro => {
        this.errorService.handle(erro);
      });
  }

  redirect(init: string) {
    if(init) {
      const route = init.toLowerCase();
      this.router.navigate(['/'+route]);
      return;
    }
    this.router.navigate(['/course']);
  }

  createPwd() {
    if(this.password === this.user.password) {
      this.securityService.changePassword(this.password, this.user.id).subscribe(() => {
        this.cleanPassword();
        this.createNewPwd = false;
      });
    }
  }

  cleanPassword() {
    this.user.password = undefined;
    this.password = undefined;
  }


}
