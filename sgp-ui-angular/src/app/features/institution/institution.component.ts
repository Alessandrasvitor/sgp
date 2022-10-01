import { Component, OnInit } from '@angular/core';
import { ConfirmationService } from 'primeng/api';
import { ExcelService } from 'src/app/shared/service/excel.service';
import { InstituitionService } from './instituition.service';

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
  permission = false;
  user = JSON.parse(localStorage.getItem('userLogin')+'');

  constructor(
    private service: InstituitionService,
    private confirmationService: ConfirmationService,
    private excelService: ExcelService
  ) { }

  ngOnInit() {
    this.loading = true;
    this.updateView();
    this.validaPermission();
  }

  validaPermission() {
    if(this.user && this.user.functionalities && this.user.functionalities.filter((func: any) => func === 'INSTITUITION').length > 0) {
      this.permission = true;
    }

  }

  updateView() {
    this.service.list().subscribe((response: any) => {
      this.institutions = response;
      this.close();
    },
    error => {
      this.close();
    });
  }

  close() {
    this.loading = false;
    this.editation = false;
    this.institution = {};
    this.viewEdit = false;
    this.labelCancel = 'Cancelar';
  }

  new() {
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
      this.updateView();
    },
    error => {
      this.close();
    });
  }

  save() {
    this.loading = true;
    if(this.institution.id) {
      this.service.put(this.institution).subscribe((response: any) => {
        this.updateView();
      },
      error => {
        this.close();
      });
    } else {
      this.service.post(this.institution).subscribe((response: any) => {
        this.updateView();
      },
      error => {
        this.close();
      });
    }
  }

  exportExcel() {
    this.excelService.exportAsExcelFile(this.institutions, 'instituições');
  }

}
