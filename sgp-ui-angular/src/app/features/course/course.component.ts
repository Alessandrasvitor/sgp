import { Component, OnInit } from '@angular/core';
import { ConfirmationService } from 'primeng/api';
import { CategoriaEnum } from 'src/app/shared/class/enums.enum';
import { ExcelService } from 'src/app/shared/service/excel.service';
import { InstituitionService } from '../institution/instituition.service';
import { CourseService } from './course.service';
import { CategoryPipe } from './../../shared/pipes/category.pipe';

@Component({
  templateUrl: './course.component.html',
  styleUrls: ['./course.component.css']
})
export class CourseComponent implements OnInit {

  loading = false;
  viewEdit = false;
  course: any = {};
  courses: any = [];
  instituitions: any = [];
  instituition: any = {};
  title: any;
  editation = false;
  labelCancel = 'Cancelar';
  categories: any = [];
  displayDialog = false;
  pageable: any = {page: 0, size: 3, totalPages: 0, totalElements: 0};

  constructor(
    private service: CourseService,
    private instituitionservice: InstituitionService,
    private confirmationService: ConfirmationService,
    private excelService: ExcelService
  ) { }

  ngOnInit() {
    this.loading = true;
    this.getInstituitions();
    this.getStatus();
    this.updateView();
  }

  updateView() {
    this.service.list(this.pageable).subscribe((response: any) => {
      this.courses = response.content;
      this.pageable.totalPages = response.totalPages;
      this.pageable.totalElements = response.totalElements;
      this.close();
    },
    error => {
      this.close();
    });
  }

  onPageChange(event: any) {
    this.updateView();
  }

  getInstituitions() {
    this.instituitionservice.listAll().subscribe( response => {
      this.instituitions = response;
    });
  }

  getStatus() {
    Object.values(CategoriaEnum).forEach( (category: any) => {
      if(isNaN(category)) {
        this.categories.push({value: category, label: (new CategoryPipe).transform(category) });
      }
    });
  }

  close() {
    this.loading = false;
    this.editation = false;
    this.course = {};
    this.viewEdit = false;
    this.displayDialog = false;
    this.labelCancel = 'Cancelar';
  }

  create() {
    this.title = 'Cadastrar';
    this.course = {};
    this.editation = true;
    this.viewEdit = true;
  }

  edit(course: any) {
    this.title = 'Editar';
    this.editation = true;
    this.course = course;
    this.getInstituition();
    this.viewEdit = true;
  }

  view(course: any) {
    this.title = 'Visualizar';
    this.course = course;
    this.getInstituition();
    this.viewEdit = true;
    this.labelCancel = 'Voltar';
  }

  getInstituition() {
    this.instituitions.forEach((element: any) => {
      if(element.id === this.course.idInstituition) {
        this.instituition = element;
      }
    });
  }

  confirmDelete(course: any) {
    this.confirmationService.confirm({
      header: 'Confirmação para deletar Curso',
      acceptLabel: 'Sim',
      rejectLabel: 'Não',
      message: 'Deseja realmente deletar o Curso '+course.name+'?',
      accept: () => {
        this.delete(course.id);
      },
      reject: () => {
        this.close();
      }
    });
  }

  start(id: any) {
    this.service.start(id).subscribe(() => {
      this.updateView();
    },
    error => {
      this.close();
    });
  }

  finishDialog(id: any) {
    this.course.id = id;
    this.displayDialog = true;
  }

  finish() {
    this.service.finish(this.course.notation, this.course.id).subscribe(() => {
      this.updateView();
    },
    error => {
      this.close();
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
    if(this.course.id) {
      this.service.put(this.course).subscribe((response: any) => {
        this.updateView();
      },
      error => {
        this.close();
      });
    } else {
      this.service.post(this.course).subscribe((response: any) => {
        this.updateView();
      },
      error => {
        this.close();
      });
    }
  }

  exportExcel() {
    this.service.listAll().subscribe((response: any) => {
      this.excelService.exportAsExcelFile(response, 'Cursos');
    },
    error => {
      this.close();
    });
  }

}
