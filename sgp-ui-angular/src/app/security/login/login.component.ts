import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { MessageService } from 'primeng/api';
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
    private messageService: MessageService
  ) { }

  ngOnInit() {

  }

  login() {
    let newPassword = false;
    if(this.user.password === "123456") {
      newPassword = true;
    }

    this.securityService.login(this.user)
      .then((response: any) => {
        if(newPassword) {
          this.user = new User();
          this.user.id = response.user.id;
          this.user.email = response.user.email;
          this.createNewPwd = true;
        } else {
          localStorage.setItem('userLogin', JSON.stringify(response.user));
          this.redirect(response.user.init);
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
    if(this.password !== this.user.password) {
      this.messageService.add({ severity: 'error', summary: 'Senhas incompatíveis!', detail: 'As senhas são diferentes.' });
    }
    this.securityService.alterPassword(this.user).subscribe( () => {
      this.createNewPwd = false;
      this.user = new User();
      this.router.navigate(['/login']);
    }, error => {
      this.messageService.add({ severity: 'error', summary: '', detail: error.message });
    });
  }


}
