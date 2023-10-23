import { Component, OnInit } from '@angular/core';
import { ConfirmationService } from 'primeng/api';
import { ExcelService } from 'src/app/shared/service/excel.service';
import { UserService } from './user.service';
import { FuncionalityEnum } from 'src/app/shared/class/enums.enum';
import { FuncionalityPipe } from 'src/app/shared/pipes/funcionality.pipe';
import { ErrorService } from 'src/app/shared/service/error.service';

@Component({
  templateUrl: './user.component.html',
  styleUrls: ['./user.component.css']
})
export class UserComponent implements OnInit {

  loading = false;
  viewEdit = false;
  user: any = {};
  users: any = [];
  title: any;
  editation = false;
  viewInit: any = [];
  labelCancel = 'Cancelar';
  functionalities: any = [];
  userLogin: any = JSON.parse(localStorage.getItem('userLogin')+'');
  displayDialog = false;
  pageable: any = {page: 0, size: 3, totalPages: 0, totalElements: 0};

  constructor(
    private service: UserService,
    private confirmationService: ConfirmationService,
    private errorService: ErrorService,
    private excelService: ExcelService
  ) { }

  ngOnInit() {
    this.loading = true;
    this.getFuncionality();
    this.getInitView();
    this.updateView();
  }

  updateView() {
    this.service.list(this.pageable).subscribe((response: any) => {
      this.users = response.content;
      this.pageable.totalPages = response.totalPages;
      this.pageable.totalElements = response.totalElements;
      this.close();
    },
    error => {
      this.close();
      this.errorService.handle(error);
    });
  }

  onPageChange(event: any) {
    this.updateView();
  }

  getInitView() {
    Object.values(FuncionalityEnum).forEach( (funcionality: any) => {
      if(isNaN(funcionality)) {
        this.viewInit.push({value: funcionality.toLowerCase(), label: (new FuncionalityPipe).transform(funcionality) });
      }
    });
  }

  getFuncionality() {
    Object.values(FuncionalityEnum).forEach( (funcionality: any) => {
      if(isNaN(funcionality)) {
        this.functionalities.push({value: funcionality, label: (new FuncionalityPipe).transform(funcionality) });
      }
    });
  }

  close() {
    this.loading = false;
    this.editation = false;
    this.user = {};
    this.viewEdit = false;
    this.displayDialog = false;
    this.labelCancel = 'Cancelar';
  }

  create() {
    this.title = 'Cadastrar';
    this.user = {};
    this.editation = true;
    this.viewEdit = true;
  }

  edit(user: any) {
    this.title = 'Editar';
    this.editation = true;
    this.user = user;
    this.viewEdit = true;
  }

  view(user: any) {
    this.title = 'Visualizar';
    this.user = user;
    this.viewEdit = true;
    this.labelCancel = 'Voltar';
  }

  confirmDelete(user: any) {
    this.confirmationService.confirm({
      header: 'Confirmação para deletar Usuário',
      acceptLabel: 'Sim',
      rejectLabel: 'Não',
      message: 'Deseja realmente deletar o Usuário '+user.name+'?',
      accept: () => {
        this.delete(user.id);
      },
      reject: () => {
        this.close();
      }
    });
  }

  resetPwd(id: any) {
    this.service.resetPwd(id).subscribe(() => {
      this.updateView();
    },
    error => {
      this.close();
      this.errorService.handle(error);
    });
  }

  setFunctionality(id: any) {
    this.service.get(id).subscribe((response: any) => {
      this.user = response;
      this.displayDialog = true;
    })
  }

  updateFunctionalities() {
    this.service.updateFunctionalities(this.user.functionalities, this.user.id).subscribe(() => {
      this.updateView();
    },
    error => {
      this.close();
      this.errorService.handle(error);
    });
  }

  delete(id: any) {
    this.service.delete(id).subscribe(() => {
      this.updateView();
    },
    error => {
      this.close();
      this.errorService.handle(error);
    });
  }

  save() {
    this.loading = true;
    if(this.user.id) {
      this.service.put(this.user).subscribe((response: any) => {
        this.updateView();
      },
      error => {
        this.errorService.handle(error);
      });
    } else {
      this.user.idUser = this.user.id; 
      this.service.post(this.user).subscribe((response: any) => {
        this.updateView();
      },
      error => {
        this.errorService.handle(error);
      });
    }
  }


  exportExcel() {
    this.service.listAll().subscribe((response: any) => {
      this.excelService.exportAsExcelFile(response, 'Usuários');
    },
    error => {
      this.close();
    });
  }

}
