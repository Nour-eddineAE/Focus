import { HttpClient, HttpHeaders, HttpRequest } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { firstValueFrom, Observable, of, throwError, Subject } from 'rxjs';
import { AppUser, LoginBody, Tokens } from 'src/app/model/user.model';
import { JwtHelperService } from '@auth0/angular-jwt';

@Injectable({
  providedIn: 'root',
})
export class UserService {
  loginURL: string = '';
  saveUserURL: string = '';
  refreshTokenURL: string = '';
  host: string = 'http://localhost:8081';
  getProfilePicURL: string = '/api/user/profilePicture/';
  jwtHelper!: JwtHelperService;
  authenticatedUserUsername!: string;
  timeStamp: number = 0;

  constructor(private httpClient: HttpClient) {
    this.loginURL = this.host + '/auth/login';
    this.saveUserURL = this.host + '/api/auth/signup';
    this.refreshTokenURL = this.host + '/api/refreshToken';
    this.jwtHelper = new JwtHelperService();

    //get the username on refresh, so we don't lose the images
    this.reloadAvatar();
  }

  // control methods

  public isAccessTokenExpired(accessToken: string): Observable<boolean> {
    return of(this.jwtHelper.isTokenExpired(accessToken));
  }

  decodeToken(token: string) {
    return this.jwtHelper.decodeToken(token);
  }

  async validateAccessToken() {
    const oldTokens: Tokens = JSON.parse(
      localStorage.getItem('authenticatedUserTokens')!
    );

    const expired = await firstValueFrom(
      this.isAccessTokenExpired(oldTokens.accessToken)
    );

    if (expired) {
      try {
        const response = await this.refreshAccessToken(oldTokens.refreshToken);
        if (response == undefined) {
          throwError(() => new Error('Problem with refresh Token'));
        } else {
          const newTokens = await firstValueFrom(response);
          localStorage.removeItem('authenticatedUserTokens');
          localStorage.setItem(
            'authenticatedUserTokens',
            JSON.stringify(newTokens)
          );
        }
      } catch (error: any) {
        console.log('ERROR REFRESHING TOKEN at userService');
        throwError(() => error);
      }
    }
  }

  getAccessToken() {
    const tokens: Tokens = JSON.parse(
      localStorage.getItem('authenticatedUserTokens')!
    );
    return tokens.accessToken;
  }

  reloadAvatar() {
    const tokens: Tokens = JSON.parse(
      localStorage.getItem('authenticatedUserTokens')!
    );
    if (tokens) {
      const refreshToken = this.decodeToken(tokens.refreshToken);
      this.authenticatedUserUsername = refreshToken.sub;
    }
  }

  // getTimeStamp returns a Date, which is a unique parameter, hence the URL is always unique,
  // this way we can avoid cache problems by changing the URL
  getTimeStamp() {
    return this.timeStamp;
  }

  getAvatarUrl() {
    return (
      this.host +
      this.getProfilePicURL +
      this.authenticatedUserUsername +
      '?ts=' +
      this.getTimeStamp()
    );
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
      // get the username typed by user
      this.authenticatedUserUsername = loginBody.username;
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
    // you can use this to perform some profile update
  }

  uploadFile(file: any) {
    const formData: FormData = new FormData();
    formData.append('file', file);

    this.validateAccessToken();

    const request = new HttpRequest(
      'POST',
      this.host + '/api/user/uploadPhoto/' + this.authenticatedUserUsername,
      formData,
      {
        reportProgress: true,
        responseType: 'text',
        headers: new HttpHeaders({
          Auhtorization: 'Bearer ' + this.decodeToken(this.getAccessToken()),
        }),
      }
    );
    return this.httpClient.request(request);
  }
}
