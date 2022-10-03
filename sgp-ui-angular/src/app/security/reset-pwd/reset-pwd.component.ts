import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { User } from 'src/app/shared/class/classes';
import { ErrorService } from 'src/app/shared/service/error.service';
import { SecurityService } from '../security.service';

@Component({
  templateUrl: './reset-pwd.component.html',
  styleUrls: ['./reset-pwd.component.css']
})
export class ResetPwdComponent implements OnInit {
  
  user: User = new User();
  password: string | undefined;

  constructor(
    private router: Router,
    private securityService: SecurityService,
    private errorService: ErrorService
  ) { }

  ngOnInit() {
    if(localStorage.getItem('email')) {
      this.user.email = localStorage.getItem('email');
    }
    localStorage.removeItem('email');
  }

  createPwd() {
    if(this.password === this.user.password) {
      this.securityService.changePassword(this.password, this.user.id)
      .then((response: any) => {
        localStorage.setItem('userLogin', JSON.stringify(response.user));
        this.router.navigate(['/'+response.user.startView]);
      })
      .catch(erro => {
        this.errorService.handle(erro);
      });
    }
  }

}
