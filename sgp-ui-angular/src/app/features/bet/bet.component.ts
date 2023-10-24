import { Component, OnInit } from '@angular/core';
import { ConfirmationService } from 'primeng/api';
import { ExcelService } from 'src/app/shared/service/excel.service';
import { BetService } from './bet.service';

@Component({
  selector: 'app-bet',
  templateUrl: './bet.component.html',
  styleUrls: ['./bet.component.css']
})
export class BetComponent implements OnInit {
  
  loading = false;
  viewEdit = false;
  bet: any = {};
  bets: any = [];
  title: any;
  editation = false;
  labelCancel = 'Cancelar';
  permission = false;
  user = JSON.parse(localStorage.getItem('userLogin')+'');
  pageable: any = {page: 0, size: 3, totalPages: 0, totalElements: 0};

  constructor(
    private service: BetService,
    private confirmationService: ConfirmationService,
    private excelService: ExcelService
  ) { }

  ngOnInit() {
    this.loading = true;
    this.updateView();
    this.validaPermission();
  }

  validaPermission() {
    if(this.user && this.user.functionalities && this.user.functionalities.filter((func: any) => func === 'BET').length > 0) {
      this.permission = true;
    }

  }

  updateView() {
    this.service.list(this.pageable).subscribe((response: any) => {
      this.bets = response.content;
      this.pageable.totalPages = response.totalPages;
      this.pageable.totalElements = response.totalElements;
      this.close();
    },
    error => {
      this.close();
    });
  }

  close() {
    this.loading = false;
    this.editation = false;
    this.bet = {};
    this.viewEdit = false;
    this.labelCancel = 'Cancelar';
  }

  new() {
    this.title = 'Cadastrar';
    this.bet = {};
    this.editation = true;
    this.viewEdit = true;
  }

  edit(bet: any) {
    this.title = 'Editar';
    this.editation = true;
    this.bet = bet;
    this.viewEdit = true;
  }

  view(bet: any) {
    this.title = 'Visualizar';
    this.bet = bet;
    this.viewEdit = true;
    this.labelCancel = 'Voltar';
  }

  confirmDelete(bet: any) {
    this.confirmationService.confirm({
      header: 'Confirmação para deletar Aposta',
      acceptLabel: 'Sim',
      rejectLabel: 'Não',
      message: 'Deseja realmente deletar a aposta '+bet.bet+'?',
      accept: () => {
        this.delete(bet.id);
      },
      reject: () => {
        this.close();
      }
    });
  }

  delete(id: any) {
    this.service.delete(id).subscribe(() => {
      this.updateView();
    },
    error => {
      this.close();
    });
  }

  save() {
    this.loading = true;
    this.bet.user = this.user;
    if(this.bet.id) {
      this.service.put(this.bet).subscribe((response: any) => {
        this.updateView();
      },
      error => {
        this.close();
      });
    } else {
      this.service.post(this.bet).subscribe((response: any) => {
        this.updateView();
      },
      error => {
        this.close();
      });
    }
  }

  exportExcel() {
    this.service.listAll().subscribe((response: any) => {
      this.excelService.exportAsExcelFile(response, 'Apostas');
    },
    error => {
      this.close();
    });
  }

}
