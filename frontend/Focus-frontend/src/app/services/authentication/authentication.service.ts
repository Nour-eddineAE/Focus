import { HttpErrorResponse } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { UUID } from 'angular2-uuid';
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

  constructor(private userService: UserService) {
    // this.userService.getAllUsers().subscribe({
    //   next: (response: AppUser[]) => {
    //     this.users = response;
    //   },
    //   error: (error: HttpErrorResponse) => {
    //     this.errorMessage = error.message;
    //   },
    // });
  }

  public async login(
    loginBody: LoginBody
  ): Promise<Observable<Tokens> | undefined> {
    try {
      let response = await this.userService.login(loginBody);
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
    localStorage.setItem('authenticatedUser', JSON.stringify(tokens));
    return of(true);
  }

  public isAuthenticated(): boolean {
    return JSON.parse(localStorage.getItem('loggedIn') || 'false');
  }

  public Logout(): Observable<boolean> {
    this.authenticatedUser = undefined;
    localStorage.removeItem('authenticatedUser');
    localStorage.removeItem('loggedIn');
    return of(true);
  }

  // public async addUser(user: AppUser): Promise<AppUser | undefined> {
  //   try {
  //     let response = await this.userService.saveUser(user);
  //     let savedUser = await firstValueFrom(response);
  //     return savedUser;
  //   } catch (error) {
  //     throwError(() => error);
  //     return undefined;
  //   }
  // }
}
