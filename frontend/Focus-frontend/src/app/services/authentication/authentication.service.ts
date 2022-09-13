import { Injectable, HostListener } from '@angular/core';
import { firstValueFrom, Observable, of, throwError } from 'rxjs';
import { AppUser, LoginBody, Tokens } from '../../model/user.model';
import { UserService } from '../users/user.service';

@Injectable({
  providedIn: 'root',
})
export class AuthenticationService {
  users: AppUser[] = [];
  authenticatedUser: AppUser | undefined;
  errorMessage: any = '';
  tokens!: Tokens;

  constructor(private userService: UserService) {}

  public async signup(appUser: AppUser) {
    try {
      let response = await this.userService.signup(appUser);
      // if this.userService.login returns undefined
      if (response == undefined) {
        return Promise.resolve(undefined);
      } else {
        this.authenticatedUser = await firstValueFrom(response);
        return Promise.resolve(true);
      }
    } catch (error: any) {
      console.log('ERROR INSIDE authenticationService.login' + error.message);
      throwError(() => error);
    }
    // if no one of the cases above was reached, return undefined
    return undefined;
  }

  public async login(
    loginBody: LoginBody
  ): Promise<Observable<Tokens> | undefined> {
    try {
      const response = await this.userService.login(loginBody);
      // if this.userService.login returns undefined
      if (response == undefined) {
        return Promise.resolve(undefined);
      } else {
        this.tokens = await firstValueFrom(response);
        return Promise.resolve(of(this.tokens));
      }
    } catch (error: any) {
      console.log('ERROR INSIDE authenticationService.login' + error.message);
      throwError(() => error);
    }
    // if no one of the cases above was reached, return undefined
    return undefined;
  }

  public authenticate(tokens: Tokens): Observable<boolean> {
    localStorage.setItem('loggedIn', 'true');
    localStorage.setItem('authenticatedUserTokens', JSON.stringify(tokens));
    return of(true);
  }

  public isAuthenticated(): boolean {
    return JSON.parse(localStorage.getItem('loggedIn') || 'false');
  }

  public Logout(): Observable<boolean> {
    this.authenticatedUser = undefined;
    localStorage.removeItem('authenticatedUserTokens');
    localStorage.removeItem('loggedIn');
    return of(true);
  }

  async validateAccessToken() {
    let oldTokens: Tokens = JSON.parse(
      localStorage.getItem('authenticatedUserTokens')!
    );

    let expired = await firstValueFrom(
      this.userService.isAccessTokenExpired(oldTokens.accessToken)
    );

    if (expired) {
      try {
        let response = await this.userService.refreshAccessToken(
          oldTokens.refreshToken
        );
        if (response == undefined) {
          throwError(() => new Error('Problem with refresh Token'));
        } else {
          let newTokens = await firstValueFrom(response);
          localStorage.removeItem('authenticatedUserTokens');
          localStorage.setItem(
            'authenticatedUserTokens',
            JSON.stringify(newTokens)
          );
        }
      } catch (error: any) {
        console.log('ERROR REFRESHING TOKEN at authService');
        throwError(() => error);
      }
    }
  }
}
