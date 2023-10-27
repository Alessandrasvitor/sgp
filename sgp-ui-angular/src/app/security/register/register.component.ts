import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { User } from 'src/app/shared/class/classes';
import { ErrorService } from 'src/app/shared/service/error.service';
import { SecurityService } from '../security.service';

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.css']
})
export class RegisterComponent implements OnInit {

  user: User = new User();
  password: string | undefined;

  constructor(
    private errorService: ErrorService,
    private securityService: SecurityService,
    private router: Router
  ) { }

  ngOnInit() {
  }  

  register() {
    if(this.password === this.user.password) {
      this.securityService.register(this.user)
      .then(() => {
        this.router.navigate(['/active-user']);
      })
      .catch(erro => {
        this.errorService.handle(erro);
      });
    } else {
      this.errorService.handle('As senhas são incompatíveis');
    }
  }

  cancel() {
    this.router.navigate(['/login']);
  }

}
