import { Injectable } from '@angular/core';
import { environment } from './../../environments/environment';
import { HttpClient, HttpHandler, HttpHeaders, HttpRequest } from '@angular/common/http';
import { MessageService } from 'primeng/api';

@Injectable({
  providedIn: 'root'
})
export class SecurityService {

  private url = `${environment.apiUrl}/auth`;

  jwtPayload: any;

  constructor( 
    private http: HttpClient,
	private messageService: MessageService
  ) { }

	login(user: any): Promise<void> {
		return this.http.post(this.url + '/login', { email: user.email, password: user.password }).toPromise()
			.then((response: any) => {
				localStorage.setItem('token', response.token);
				return response;
			})
			.catch((response: any) => {
				if (response.status === 401 && response.statusText === 'Unauthorized') {
					this.messageService.add({ severity: 'error', summary: 'Acesso não autorizado !', detail: 'Usuário e/ou senha incorretos.' });
					return Promise.reject('Acesso não autorizado (usuário e/ou senha incorretos).');
				}
				return Promise.reject(response);
			});
	}

	cleanToken() {
		localStorage.removeItem('token');
	}
  
	logout(id: number) {
		this.jwtPayload = null;
		return this.http.put(this.url + '/logout/'+ id, {
			headers: {
				session: localStorage.getItem('token'),
			}
		});
	}

	changePassword(pwd: any, id: any) {
		return this.http.put(this.url + '/changePassword/'+id, {password: pwd});
	}

	intercept(req: HttpRequest<any>, next: HttpHandler) {

		if (localStorage.getItem('token')) {
		  req = req.clone({
			setHeaders: {
			  Authorization: sessionStorage.getItem('beares') + ' ' + localStorage.getItem('token')
			}
		  })
		}
	
		return next.handle(req);
	
	  }

	  getAuthorizated() {
		return {headers: new HttpHeaders().set('Authorization', `Bearer ${localStorage.getItem('token')}`)};
	  }

}
