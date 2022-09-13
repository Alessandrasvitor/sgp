import { Injectable } from '@angular/core';
import { environment } from './../../environments/environment';
import { HttpClient } from '@angular/common/http';
import { MessageService } from 'primeng/api';
import { JwtHelperService } from '@auth0/angular-jwt';

@Injectable({
  providedIn: 'root'
})
export class SecurityService {

  private url = `${environment.apiUrl}/auth`;
  private jwtHelper: JwtHelperService | undefined;

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

	alterPassword(user:any) {
		return this.http.get(this.url + '/update/'+ user.id + '/' + user.password);
	}

	cleanToken() {
		localStorage.removeItem('token');
	}
  
	logout(id: number) {
		return this.http.put(this.url + '/logout/'+ id, {
			headers: {
				session: localStorage.getItem('token'),
			}
		});
	}

	isLogged() {
		let hasToken = localStorage.getItem('token') == null;
		
		if (hasToken) {
			hasToken = !this.isAccessToken();
		}
		return hasToken;
	}

	isAccessToken() {
		this.jwtHelper = new JwtHelperService();
		const token = localStorage.getItem('token');
		return !token || this.jwtHelper.isTokenExpired(token);
	}

}
