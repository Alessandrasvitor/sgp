import { Component, OnInit } from '@angular/core';
import { SecurityService } from './security/security.service';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent implements OnInit {
  isLogged = false;
  interval: any;
  constructor( private securityService: SecurityService ) {}

  ngOnInit(): void {
    this.interval = setInterval(() => {
      if(this.securityService.isLogged()) {
        this.isLogged = true;
      } else {
        this.isLogged = false;
      }
    }, 1000);
  }







}
