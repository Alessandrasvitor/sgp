import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { MenuItem } from 'primeng/api';
import { SecurityService } from 'src/app/security/security.service';
import { MenuPipe } from '../pipes/menu.pipe';

@Component({
  selector: 'app-nav-bar',
  templateUrl: './nav-bar.component.html',
  styleUrls: ['./nav-bar.component.css']
})
export class NavBarComponent implements OnInit {

  items: MenuItem[] = [{label: 'Home', icon: 'pi pi-fw pi-home', routerLink: '/home'}];
  itemsUser: MenuItem[] = [];
  user: any = JSON.parse(localStorage.getItem('userLogin')+'');

  constructor(
    private securityService: SecurityService,
    private router: Router,
    ) { }

  ngOnInit() {

    if(this.user) {
      this.user.functionalities.forEach((functionality: any) => {
        const item =(new MenuPipe).transform(functionality);
        if(item) {
          this.items.push(item);
        }
      });
      this.itemsUser.push({
        label: 'Logout',
        icon: 'pi pi-power-off',
        command: () => { this.logout()}
      });
    }
  }

  logout() {
    this.securityService.logout(this.user.id).subscribe(() => {
      this.router.navigate(['/login']);
    });
  }

}
