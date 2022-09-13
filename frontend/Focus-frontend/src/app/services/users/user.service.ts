import { HttpClient, HttpHeaders } from '@angular/common/http';
import { HostListener, Injectable } from '@angular/core';
import { Observable, of, throwError } from 'rxjs';
import { AppUser, LoginBody, Tokens } from 'src/app/model/user.model';
import { JwtHelperService } from '@auth0/angular-jwt';
import { AuthenticationService } from '../authentication/authentication.service';

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
  userProfilePictureURL =
    this.host + this.getProfilePicURL + this.authenticatedUserUsername;

  constructor(private httpClient: HttpClient) {
    this.loginURL = this.host + '/auth/login';
    this.saveUserURL = this.host + '/api/auth/signup';
    this.refreshTokenURL = this.host + '/api/refreshToken';
    this.jwtHelper = new JwtHelperService();

    const tokens: Tokens = JSON.parse(
      localStorage.getItem('authenticatedUserTokens')!
    );
    const refreshToken = this.decodeToken(tokens.refreshToken);
    this.authenticatedUserUsername = refreshToken.sub;
    this.userProfilePictureURL =
      this.host + this.getProfilePicURL + this.authenticatedUserUsername;
  }

  @HostListener('window:load', ['$event'])
  onRefresh(event: Event) {
    const tokens: Tokens = JSON.parse(
      localStorage.getItem('authenticatedUserTokens')!
    );
    const refreshToken = this.decodeToken(tokens.refreshToken);
    this.authenticatedUserUsername = refreshToken.sub;
    this.userProfilePictureURL =
      this.host + this.getProfilePicURL + this.authenticatedUserUsername;
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

  decodeToken(token: string) {
    return this.jwtHelper.decodeToken(token);
  }
  // authentication required requests
  updateProfile() {
    // you can use this to save some profile update
  }
}
