import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable, take, throwError } from 'rxjs';
import { AppUser, LoginBody, Tokens } from 'src/app/model/user.model';

@Injectable({
  providedIn: 'root',
})
export class UserService {
  loginURL: string = '';
  saveUserUrl: string = '';
  proxy: string = 'http://localhost:8081';
  constructor(private httpClient: HttpClient) {
    this.loginURL = this.proxy + '/auth/login';

    this.saveUserUrl = this.proxy + '/api/auth/signup';
  }

  saveUser(user: AppUser): Observable<boolean> {
    return this.httpClient.post<boolean>(this.saveUserUrl, user);
  }

  updateProfile() {
    // you can use this to save some profile update
  }
  /**
   *
   * @param loginBody
   * @returns promise of observable of Tokens,
   */
  login(loginBody: LoginBody): Promise<Observable<Tokens> | undefined> {
    try {
      return Promise.resolve(
        this.httpClient.post<Tokens>(this.loginURL, JSON.stringify(loginBody))
      );
    } catch (error: any) {
      console.log('ERROR INSIDE userService.login' + error.message);
      throwError(() => error);
    }
    return Promise.resolve(undefined);
  }
}
