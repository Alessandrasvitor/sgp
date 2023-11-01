import { Component, OnInit } from '@angular/core';
import { ConfirmationService, MessageService } from 'primeng/api';
import { ExcelService } from 'src/app/shared/service/excel.service';
import { LotteryService } from './lottery.service';
import { TypeLotteryEnum } from 'src/app/shared/class/enums.enum';
import { TypeLotteryPipe } from 'src/app/shared/pipes/type-lottery.pipe';
import { MaxNumberLotteryPipe } from 'src/app/shared/pipes/max-number-lottery.pipe';
import { ErrorService } from 'src/app/shared/service/error.service';

@Component({
  templateUrl: './lottery.component.html',
  styleUrls: ['./lottery.component.css']
})
export class LotteryComponent implements OnInit {

  loading = false;
  viewEdit = false;
  displayGenerate = false;
  displayResult = false;
  displayAdd = false;
  addBotton = false;
  removeBotton = false;
  lottery: any = {};
  lotterys: any = [];
  typesLottery: any = [];
  title: any;
  editation = false;
  labelCancel = 'Cancelar';
  permission = false;
  lotteryType = '';
  numberSelect: any = [];
  pt: any;
  createding = false;
  typeNumber = 'BET';
  pageable: any = {page: 0, size: 3, totalPages: 0, totalElements: 0};
  successMessage = 'Operação realizada com sucesso!';

  constructor(
    private confirmationService: ConfirmationService,
    private messageService: MessageService,
    private excelService: ExcelService,
    private errorService: ErrorService,
    private service : LotteryService
    ) { }

  ngOnInit() {
    this.loading = true;
    this.getTypesLottery();
    this.updateView();
    this.pt = {
      firstDayOfWeek: 0,
      dayNames: ['Domingo', 'Segunda', 'Terça', 'Quarta', 'Quinta', 'Sexta', 'Sábado'],
      dayNamesShort: ['Dom', 'Seg', 'Ter', 'Qua', 'Qui', 'Sex', 'Sab'],
      dayNamesMin: ['Do', 'Se', 'Te', 'Qu', 'Qu', 'Se', 'Sa'],
      monthNames: ['Janeiro', 'Fevereiro', 'Março', 'Abril', 'Maio', 'Junho', 'Julho',
        'Agosto', 'Setembro', 'Outubro', 'Novembro', 'Dezembro'],
      monthNamesShort: ['Jan', 'Fev', 'Mar', 'Abr', 'Mai', 'Jun', 'Jul', 'Ago', 'Set', 'Out', 'Nov', 'Dez'],
      today: 'Hoje',
      clear: 'Limpar'
    };
  }

  updateView() {
    this.service.list(this.pageable).subscribe((response: any) => {
      this.lotterys = response.content;
      this.pageable.totalPages = response.totalPages;
      this.pageable.totalElements = response.totalElements;
      this.close();
    },
    error => {
      this.errorService.handle(error);
    });
  }

  onPageChange(event: any) {
    this.updateView();
  }

  generateLottery() {
    this.displayGenerate = true;
  }

  close() {
    this.displayGenerate = false;
    this.loading = false;
    this.editation = false;
    this.lottery = {};
    this.viewEdit = false;
    this.createding = false;
    this.displayResult = false;
    this.displayAdd = false;
    this.addBotton = false;
    this.removeBotton = false;
    this.lotteryType = '';
    this.labelCancel = 'Cancelar';
    this.typeNumber = 'BET';
  }

  create() {
    this.title = 'Cadastrar';
    this.typeNumber = 'BET';
    this.lottery = {};
    this.createding = true;
    this.editation = true;
    this.viewEdit = true;
  }

  edit(bet: any) {
    this.title = 'Editar';
    this.typeNumber = 'BET';
    this.editation = true;
    this.lottery = bet;
    this.viewEdit = true;
  }

  view(bet: any) {
    this.title = 'Visualizar';
    this.lottery = bet;
    this.viewEdit = true;
    this.labelCancel = 'Voltar';
  }

  generate() {
    this.service.generate(this.lotteryType).subscribe((response: any) => {
      this.close();
      this.lottery = response;
      this.title = 'Cadastrar';
      this.typeNumber = 'BET';
      this.editation = true;
      this.viewEdit = true;
    },
    error => {
      this.errorService.handle(error);
    });
  }

  confirmDelete(lottery: any) {
    this.confirmationService.confirm({
      header: 'Confirmação para deletar Aposta',
      acceptLabel: 'Sim',
      rejectLabel: 'Não',
      message: 'Deseja realmente deletar a aposta com os números: '+lottery.bet+'?',
      accept: () => {
        this.delete(lottery.id);
      },
      reject: () => {
        this.close();
      }
    });
  }

  delete(id: any) {
    this.service.delete(id).subscribe(() => {
      this.messageService.add({ severity: 'success', detail: this.successMessage});
      this.updateView();
    },
    error => {
      this.errorService.handle(error);
    });
  }

  save() {
    this.loading = true;
    if(this.lottery.id) {
      this.service.put(this.lottery).subscribe((response: any) => {
        this.messageService.add({ severity: 'success', detail: this.successMessage});
        this.updateView();
      },
      error => {
        this.errorService.handle(error);
      });
    } else {
      this.service.post(this.lottery).subscribe((response: any) => {
        this.messageService.add({ severity: 'success', detail: this.successMessage});
        this.updateView();
      },
      error => {
        this.errorService.handle(error);
      });
    }
  }

  getTypesLottery() {
    Object.values(TypeLotteryEnum).forEach( (typesLottery: any) => {
      if(isNaN(typesLottery)) {
        this.typesLottery.push({value: typesLottery, label: (new TypeLotteryPipe).transform(typesLottery) });
      }
    });
  }

  addResult(lottery: any) {
    this.typeNumber = 'RESULT';
    this.lottery = lottery;
    this.displayResult = true;
  }

  selectNumberAdd(value: any) {
    let itens = [];
    if(this.typeNumber === 'BET') {
      if(this.lottery.bet && this.lottery.bet.length > 0) {
        itens = this.lottery.bet.split('-');
      }
    } else {
      if(this.lottery.result && this.lottery.result.length > 0) {
        itens = this.lottery.result.split('-');
      }
    }

    itens.push(value);
    itens = itens.sort((a:any, b:any) => (a < b) ? -1 : 1);
    let bet = '';
    itens.forEach((item: any) => {
      bet +=item+'-';
    });
    if(this.typeNumber === 'BET') {
      this.lottery.bet = bet.substring(0, bet.length - 1);
    } else {
      this.lottery.result = bet.substring(0, bet.length - 1);
    }
    this.displayAdd = false;
  }

  selectNumberRemove(value: any) {
    const bets = this.numberSelect.filter((item: any) => item !== value);
    this.closeDisplayAdd(bets);
  }

  closeDisplayAdd(itens: any) {
    let bet = '';
    itens.forEach((item: any) => {
      bet +=item+'-';
    });
    if(this.typeNumber === 'BET') {
      this.lottery.bet = bet.substring(0, bet.length - 1);
    } else {
      this.lottery.result = bet.substring(0, bet.length - 1);
    }
    this.displayAdd = false;
    this.addBotton = false;
    this.removeBotton = false;
  }

  addNumber() {
    this.getMaxLottery();
    this.removeBotton = false;
    this.addBotton = true;
    this.displayAdd = true;
  }

  removeNumber() {
    this.getNumbersLottery();
    this.addBotton = false;
    this.removeBotton = true;
    this.displayAdd = true;
  }

  getMaxLottery() {
    this.numberSelect = [];
    const max = (new MaxNumberLotteryPipe).transform(this.lottery.type);
    let itens = '';
    if(this.typeNumber === 'BET') {
      if(this.lottery.bet && this.lottery.bet.length > 0) {
        itens = this.lottery.bet;
      }
    } else {
      if(this.lottery.result && this.lottery.result.length > 0) {
        itens = this.lottery.result;
      }
    }    
    for(let index = 1; index <= max; index++) {
      let item = ''+index;
      if(index <= 9) {
        item = '0'+index;
      }
      if(itens.indexOf(item) === -1) {
          this.numberSelect.push(item);
      }
    }
  }

  getNumbersLottery() {
    this.numberSelect = [];
    if(this.typeNumber === 'BET') {
      this.numberSelect = this.lottery.bet.split('-');
    } else {
      this.numberSelect = this.lottery.result.split('-');
    } 
  }

  exportExcel() {
    this.service.listAll().subscribe((response: any) => {
      this.excelService.exportAsExcelFile(response, 'Loterias');
    },
    error => {
      this.errorService.handle(error);
    });
  }

}
