import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { SecurityService } from 'src/app/security/security.service';
import { environment } from 'src/environments/environment';

@Injectable({
  providedIn: 'root'
})
export class UserService {
  
  private url = `${environment.apiUrl}/user`;

  constructor(
    private http: HttpClient,
    private securityService: SecurityService
  ) { }

  listAll() {
		return this.http.get(this.url, this.securityService.getAuthorizated());
  }

  list(pageable:  any) {
		return this.http.get(this.url + '?page=' + pageable.page + '&size=' + pageable.size, this.securityService.getAuthorizated());
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

  updateFunctionalities(functionalities: any, id: any) {
    return this.http.put(this.url + '/functionalities/' + id, {functionalities: functionalities}, this.securityService.getAuthorizated());
  }

  resetPwd(id: any) {
    return this.http.get(this.url + '/reset/' + id, this.securityService.getAuthorizated());
  }

  delete(id: any) {
		return this.http.delete(this.url+'/'+id, this.securityService.getAuthorizated());
  }

}
