import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';

import { SecurityService } from 'src/app/security/security.service';

import { environment } from 'src/environments/environment';

@Injectable({
  providedIn: 'root'
})
export class InstituitionService {
  
  private url = `${environment.apiUrl}/instituition`;

  constructor(
    private http: HttpClient,
    private securityService: SecurityService
  ) { 
  }

  list() {
		return this.http.get(this.url, this.securityService.getAuthorizated());
  }

  get(id: number) {
    return this.http.get(this.url + '/' + id, this.securityService.getAuthorizated());
  }

  put(institution: any) {
    return this.http.put(this.url + '/' + institution.id, institution, this.securityService.getAuthorizated());
  }

  post(institution: any) {
		return this.http.post(this.url, institution, this.securityService.getAuthorizated());
  }

  delete(id: any) {
		return this.http.delete(this.url+'/'+id, this.securityService.getAuthorizated());
  }

}
