import { Component, OnInit } from '@angular/core';
import { ConfirmationService, MessageService } from 'primeng/api';
import { ExcelService } from 'src/app/shared/service/excel.service';
import { InstituitionService } from './instituition.service';
import { ErrorService } from 'src/app/shared/service/error.service';

@Component({
  templateUrl: './institution.component.html',
  styleUrls: ['./institution.component.css']
})
export class InstitutionComponent implements OnInit {

  loading = false;
  viewEdit = false;
  institution: any = {};
  institutions: any = [];
  title: any;
  editation = false;
  labelCancel = 'Cancelar';
  pageable: any = {page: 0, size: 3, totalPages: 0, totalElements: 0};
  successMessage = 'Operação realizada com sucesso!';

  constructor(
    private confirmationService: ConfirmationService,
    private messageService: MessageService,
    private service: InstituitionService,
    private errorService: ErrorService,
    private excelService: ExcelService
  ) { }

  ngOnInit() {
    this.loading = true;
    this.updateView();
  }

  updateView() {
    this.service.list(this.pageable).subscribe((response: any) => {
      this.institutions = response.content;
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

  close() {
    this.loading = false;
    this.editation = false;
    this.institution = {};
    this.viewEdit = false;
    this.labelCancel = 'Cancelar';
  }

  create() {
    this.title = 'Cadastrar';
    this.institution = {};
    this.editation = true;
    this.viewEdit = true;
  }

  edit(institution: any) {
    this.title = 'Editar';
    this.editation = true;
    this.institution = institution;
    this.viewEdit = true;
  }

  view(institution: any) {
    this.title = 'Visualizar';
    this.institution = institution;
    this.viewEdit = true;
    this.labelCancel = 'Voltar';
  }

  confirmDelete(institution: any) {
    this.confirmationService.confirm({
      header: 'Confirmação para deletar Instituição',
      acceptLabel: 'Sim',
      rejectLabel: 'Não',
      message: 'Deseja realmente deletar a instituição '+institution.name+'?',
      accept: () => {
        this.delete(institution.id);
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
    if(this.institution.id) {
      this.service.put(this.institution).subscribe((response: any) => {
        this.messageService.add({ severity: 'success', detail: this.successMessage});
        this.updateView();
      },
      error => {
        this.errorService.handle(error);
      });
    } else {
      this.service.post(this.institution).subscribe((response: any) => {
        this.messageService.add({ severity: 'success', detail: this.successMessage});
        this.updateView();
      },
      error => {
        this.errorService.handle(error);
      });
    }
  }

  getRating(value: any) {
    return value / 2;
  }

  exportExcel() {
    this.service.listAll().subscribe((response: any) => {
      this.excelService.exportAsExcelFile(response, 'Instituições');
    },
    error => {
      this.errorService.handle(error);
    });
  }

}
