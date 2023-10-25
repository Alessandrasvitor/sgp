import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { SecurityService } from 'src/app/security/security.service';
import { environment } from 'src/environments/environment';

@Injectable({
  providedIn: 'root'
})
export class LotteryService {

  private url = `${environment.apiUrl}/lottery`;

  constructor(
    private http: HttpClient,
    private securityService: SecurityService
  ) { 
  }

  listAll() {
		return this.http.get(this.url+'/all', this.securityService.getAuthorizated());
  }

  list(pageable: any) {
		return this.http.get(this.url + '?page=' + pageable.page + '&size=' + pageable.size, this.securityService.getAuthorizated());
  }

  get(id: number) {
    return this.http.get(this.url + '/' + id, this.securityService.getAuthorizated());
  }

  generate(type: any) {
    return this.http.get(this.url + '/generate/'+type, this.securityService.getAuthorizated());
  }
  put(lottery: any) {
    return this.http.put(this.url + '/' + lottery.id, lottery, this.securityService.getAuthorizated());
  }

  post(lottery: any) {
		return this.http.post(this.url, lottery, this.securityService.getAuthorizated());
  }

  delete(id: any) {
		return this.http.delete(this.url+'/'+id, this.securityService.getAuthorizated());
  }

}
