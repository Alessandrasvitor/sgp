import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';

import { SecurityService } from 'src/app/security/security.service';
import { environment } from 'src/environments/environment';

@Injectable({
  providedIn: 'root'
})
export class CourseService {
  
  private url = `${environment.apiUrl}/course`;

  constructor(
    private http: HttpClient,
    private securityService: SecurityService
  ) { }

  list() {
		return this.http.get(this.url, this.securityService.getAuthorizated());
  }

  get(id: number) {
    return this.http.get(this.url + '/' + id, this.securityService.getAuthorizated());
  }

  put(course: any) {
    return this.http.put(this.url + '/' + course.id, course, this.securityService.getAuthorizated());
  }

  post(course: any) {
		return this.http.post(this.url, course, this.securityService.getAuthorizated());
  }

  finish(notation: any, id: any) {
    return this.http.put(this.url + '/finish/' + id, {notation: notation}, this.securityService.getAuthorizated());
  }

  start(id: number) {
    return this.http.get(this.url + '/start/' + id, this.securityService.getAuthorizated());
  }

  delete(id: any) {
		return this.http.delete(this.url+'/'+id, this.securityService.getAuthorizated());
  }

}
