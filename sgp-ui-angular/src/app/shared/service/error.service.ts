import { Injectable } from '@angular/core';
import { HttpErrorResponse } from '@angular/common/http';

import { MessageService } from 'primeng/api';

@Injectable()
export class ErrorService {

  constructor(
    private messageService: MessageService
  ) { }

  handle(errorResponse: any) {
    let msg: string;

    if (typeof errorResponse === 'string') {
      msg = errorResponse;
    } else if(errorResponse instanceof HttpErrorResponse && errorResponse.status >= 400 && errorResponse.status <= 404) {
      msg = errorResponse.error.userMessage;
    } else if (errorResponse instanceof HttpErrorResponse
      && errorResponse.status >= 405 && errorResponse.status <= 499) {
      msg = 'Ocorreu um erro ao processar a sua solicitação';

      if (errorResponse.status === 403) {
        msg = 'Você não tem permissão para executar esta ação';
      }

    } else {
      msg = 'Erro ao processar serviço remoto. Tente novamente.';
      console.error('Ocorreu um erro', errorResponse);
    }

    this.messageService.add({ severity: 'error', detail: msg });
  }

}
