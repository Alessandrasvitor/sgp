import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';

@Component({
  selector: 'app-paginator',
  templateUrl: './paginator.component.html',
  styleUrls: ['./paginator.component.css']
})
export class PaginatorComponent implements OnInit {

  @Input() pageable: any = {page: 0, size: 3, totalPages: 0, totalElements: 0, first: 0};

  @Output() OnUpdateTable = new EventEmitter();

  constructor() { }

  ngOnInit() {
    this.onPageChange({page: 0, rows: this.pageable.size, first: 0});
  }

  onPageChange(event: any) {
    this.pageable.page = event.page;
    this.pageable.size = event.rows;
    this.pageable.first = event.first;
    this.OnUpdateTable.emit(event);
  }

}
