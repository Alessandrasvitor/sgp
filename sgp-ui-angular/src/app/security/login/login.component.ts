import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { User } from 'src/app/shared/class/user';
import { ErrorService } from 'src/app/shared/service/error.service';
import { SecurityService } from '../security.service';

@Component({
  selector: 'app-login',
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
  ) { }

  ngOnInit() {

  }

  login() {
    let newPassword = false;
    if(this.user.password === "bh123456") {
      newPassword = true;
    }

    this.securityService.login(this.user)
      .then(response => {
        /*
        if(newPassword) {
          this.createNewPwd = true;
        } else {
          localStorage.setItem('userLogin', JSON.stringify(response));
          this.router.navigate(['/user']);
          //this.router.navigate(['/'+this.user.init]);
        }*/
        localStorage.setItem('userLogin', JSON.stringify(response));
        this.router.navigate(['/user']);
      })
      .catch(erro => {
        this.errorService.handle(erro);
      });

  }

  createPwd() {
    if(this.password !== this.user.password) {


    }
    this.createNewPwd = false;



  }


}
