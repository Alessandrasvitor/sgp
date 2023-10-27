import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent implements OnInit {
  constructor( private router: Router ) {}

  ngOnInit(): void { }

  ifConectView() {
    return this.router.url !== '/login' &&
            this.router.url !== '/page-not-found' &&
            this.router.url !== '/reset-pwd' &&
            this.router.url !== '/active-user' &&
            this.router.url !== '/register';
  }

}
