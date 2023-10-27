import { Injectable } from '@angular/core';
import { environment } from './../../environments/environment';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { MessageService } from 'primeng/api';
import { JwtHelperService } from '@auth0/angular-jwt';
import { Router } from '@angular/router';

@Injectable({
  providedIn: 'root'
})
export class SecurityService {

	private url = `${environment.apiUrl}/auth`;
	private jwtHelper: JwtHelperService | undefined;

	constructor(
		private http: HttpClient,
		private messageService: MessageService,
		private router: Router
	) { }

	login(user: any): Promise<void> {
		return this.http.post(this.url + '/login', { email: user.email, password: user.password }).toPromise()
			.then((response: any) => {
				return response;
			})
			.catch((response: any) => {
				return this.createHandle(response);
			});
	}

	register(user: any): Promise<void> {
		return this.http.post(this.url + '/register', user).toPromise()
			.then((response: any) => {
				return response;
			})
			.catch((response: any) => {
				return this.createHandle(response);
			});
	}

	resetCheckerCode(user: any): Promise<void> {
		return this.http.put(this.url + '/reset-checker-code', user).toPromise()
			.then((response: any) => {
				return response;
			})
			.catch((response: any) => {
				return this.createHandle(response);
			});
	}

	activeUser(user: any): Promise<void> {
		return this.http.put(this.url + '/atctive', user).toPromise()
			.then((response: any) => {
				this.saveLocalStorege(response);
				return response;
			})
			.catch((response: any) => {
				return this.createHandle(response);
			});
	}

	changePassword(pwd: any, user: any) {
		return this.http.put(this.url + '/change-password/'+pwd, user).toPromise()
			.then((response: any) => {
				this.router.navigate(['/active-user']);
			})
			.catch((response: any) => {
				return this.createHandle(response);
			});
	}

	saveLocalStorege(response: any) {
		localStorage.setItem('token', response.token);
		localStorage.setItem('userLogin', JSON.stringify(response.user));
	}

	createHandle(error: any) {
		if (error.status === 401 && error.statusText === 'Unauthorized') {
			this.messageService.add({ severity: 'error', summary: 'Acesso não autorizado !', detail: 'Usuário e/ou senha incorretos.' });
			return Promise.reject('Acesso não autorizado (usuário e/ou senha incorretos).');
		}
		return Promise.reject(error);
	}

	cleanLocalStorege() {
    	localStorage.clear();
	}

	logout(id: number) {
    this.cleanLocalStorege();
		return this.http.put(this.url + '/logout/'+ id, {
			headers: {
				session: localStorage.getItem('token'),
			}
		});
	}

	validateAccessToken() {
		this.jwtHelper = new JwtHelperService();
		const token = localStorage.getItem('token');
		if(token) {
			this.isJwtExpired(token, this.jwtHelper);
		} else {
			this.router.navigate(['/login']);
		}
	}

	isJwtExpired(token: any, jwtHelper: JwtHelperService) {
		if(jwtHelper.isTokenExpired(token)) {
			const date = new Date();
			date.setMinutes(date.getMinutes() - 11);
			const dateExpiration = jwtHelper.getTokenExpirationDate(token);
			if(dateExpiration && dateExpiration < date) {
				this.router.navigate(['/login']);
			} else {
				this.updateToken();
			}
		}
	}

	updateToken() {
		const emailUser = JSON.parse(localStorage.getItem("userLogin")+'').email;
		return this.http.post(this.url + '/update-token', { email: emailUser }).toPromise()
			.then((response: any) => {
				this.saveLocalStorege(response);
				return response;
			})
			.catch((response: any) => {
				return this.createHandle(response);
			});
	}

	getAuthorizated() {
		return {headers: new HttpHeaders().set('Authorization', `Bearer ${localStorage.getItem('token')}`)};
	}

}
