import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable, of, take, throwError } from 'rxjs';
import { AppUser, LoginBody, Tokens } from 'src/app/model/user.model';
import { JwtHelperService } from '@auth0/angular-jwt';

@Injectable({
  providedIn: 'root',
})
export class UserService {
  loginURL: string = '';
  saveUserURL: string = '';
  refreshTokenURL: string = '';
  proxy: string = 'http://localhost:8081';

  jwtHelper!: JwtHelperService;

  constructor(private httpClient: HttpClient) {
    this.loginURL = this.proxy + '/auth/login';
    this.saveUserURL = this.proxy + '/api/auth/signup';
    this.refreshTokenURL = this.proxy + '/api/refreshToken';
    this.jwtHelper = new JwtHelperService();
  }

  // control methods

  public isAccessTokenExpired(accessToken: string): Observable<boolean> {
    return of(this.jwtHelper.isTokenExpired(accessToken));
  }

  //no authentication required requests
  signup(user: AppUser): Promise<Observable<AppUser> | undefined> {
    try {
      return Promise.resolve(
        this.httpClient.post<AppUser>(this.saveUserURL, user)
      );
    } catch (error: any) {
      console.log('ERROR INSIDE userService.signup  ' + error.message);
      throwError(() => error);
    }
    return Promise.resolve(undefined);
  }

  login(loginBody: LoginBody): Promise<Observable<Tokens> | undefined> {
    try {
      return Promise.resolve(
        this.httpClient.post<Tokens>(this.loginURL, JSON.stringify(loginBody))
      );
    } catch (error: any) {
      console.log('ERROR INSIDE userService.login  ' + error.message);
      throwError(() => error);
    }
    return Promise.resolve(undefined);
  }

  refreshAccessToken(
    refreshToken: string
  ): Promise<Observable<Tokens> | undefined> {
    const refreshTokenHttpOptions = {
      headers: new HttpHeaders({
        Authorization: 'Bearer ' + refreshToken,
      }),
    };

    try {
      return Promise.resolve(
        this.httpClient.get<Tokens>(
          this.refreshTokenURL,
          refreshTokenHttpOptions
        )
      );
    } catch (error: any) {
      console.log(
        'ERROR INSIDE userService.refreshAccessToken  ' + error.message
      );
      throwError(() => error);
    }
    return Promise.resolve(undefined);
  }

  // authentication required requests
  updateProfile() {
    // you can use this to save some profile update
  }
}
