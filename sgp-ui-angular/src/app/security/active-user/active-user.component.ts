import { Component, OnInit } from '@angular/core';
import { ErrorService } from 'src/app/shared/service/error.service';
import { SecurityService } from '../security.service';
import { Router } from '@angular/router';
import { MessageService } from 'primeng/api';

@Component({
  selector: 'app-active-user',
  templateUrl: './active-user.component.html',
  styleUrls: ['./active-user.component.css']
})
export class ActiveUserComponent implements OnInit {

  user: any = {};
  resetActive = false;
  resetActiveTime = false;
  resetActiveTimer = 'Aguarde 2 minutos para reenviar';
  timerInterval: any;
  successMessage = 'Operação realizada com sucesso!';

  constructor(
    private securityService: SecurityService,
    private messageService: MessageService,
    private errorService: ErrorService,
    private router: Router
  ) { }

  ngOnInit() {
    this.activeReset();
  }

  activeReset() {
    this.resetActive = true;
  }

  active() {
    this.securityService.activeUser(this.user)
    .then((response: any) => {
      this.messageService.add({ severity: 'success', summary: response.user.name, detail: 'Bem Vindo ao Sistema de gestão pessoal!' });
      this.router.navigate(['/'+response.user.startView]);
    })
    .catch(erro => {
      this.errorService.handle(erro);
    });
  }

  resetCheckerCode() {
    this.resetActive = false;
    this.resetActiveTime = true;
    this.countResetActiveTime();
    this.securityService.resetCheckerCode(this.user)
    .then(() => {
      this.messageService.add({ severity: 'success', detail: 'Mensagem reenviada com sucesso!'});
    })
    .catch(erro => {
      this.errorService.handle(erro);
    });
  }

  countResetActiveTime() {
    let timeWait = 120000;
    setTimeout( () => {
      this.resetActiveTime = false;
      this.resetActive = true;
      clearInterval(this.timerInterval);
      this.resetActiveTimer = 'Aguarde 2 minutos para reenviar';
    }, 120000);

    this.timerInterval = setInterval( () => {
      timeWait -= 1000;
      let secondsValue = (timeWait / 1000) % 60;
      let minutesValue = timeWait / 1000 / 60;
      let minutes = (''+minutesValue).slice(0, 1)
      let seconds = ''+secondsValue;
      if(secondsValue <= 9) {
        seconds = '0'+secondsValue;
      }
      this.resetActiveTimer = 'Aguarde '+minutes+':'+seconds+' minutos para reenviar';
    }, 1000);

  }

}
