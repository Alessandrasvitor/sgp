import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { SecurityService } from 'src/app/security/security.service';
import { environment } from 'src/environments/environment';

@Injectable({
  providedIn: 'root'
})
export class BetService {
  
  private url = `${environment.apiUrl}/bet`;

  constructor(
    private http: HttpClient,
    private securityService: SecurityService
  ) { 
  }

  listAll() {
		return this.http.get(this.url, this.securityService.getAuthorizated());
  }

  list(pageable: any) {
		return this.http.get(this.url + '?page=' + pageable.page + '&size=' + pageable.size, this.securityService.getAuthorizated());
  }

  get(id: number) {
    return this.http.get(this.url + '/' + id, this.securityService.getAuthorizated());
  }

  put(bet: any) {
    return this.http.put(this.url + '/' + bet.id, bet, this.securityService.getAuthorizated());
  }

  post(bet: any) {
		return this.http.post(this.url, bet, this.securityService.getAuthorizated());
  }

  delete(id: any) {
		return this.http.delete(this.url+'/'+id, this.securityService.getAuthorizated());
  }

}
