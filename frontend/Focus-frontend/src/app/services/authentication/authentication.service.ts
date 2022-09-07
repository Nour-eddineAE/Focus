import { HttpErrorResponse } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { UUID } from 'angular2-uuid';
import { firstValueFrom, Observable, of, throwError } from 'rxjs';
import { AppUser } from '../../model/user.model';
import { UserService } from '../users/user.service';

@Injectable({
  providedIn: 'root',
})
export class AuthenticationService {
  users: AppUser[] = [];
  authenticatedUser: AppUser | undefined;
  errorMessage: any = '';

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
    usernameOrEmail: string,
    password: string
  ): Promise<Observable<AppUser>> {
    try {
      let response = await this.userService.getUserByUsername(usernameOrEmail);
      this.authenticatedUser = await firstValueFrom(response);
    } catch (error) {
      this.errorMessage = error;
    }
    if (this.authenticatedUser == undefined) {
      return throwError(() => new Error('User not found'));
    }
    if (this.authenticatedUser.password != password) {
      return throwError(() => new Error('Bad credentials'));
    }
    return Promise.resolve(of(this.authenticatedUser));
  }

  public authenticate(appUser: AppUser): Observable<boolean> {
    this.authenticatedUser = appUser;
    localStorage.setItem('loggedIn', 'true');
    localStorage.setItem(
      'authenticatedUser',
      JSON.stringify({
        username: appUser.username,
        roles: appUser.roles,
        jwt: 'JWT_TOKEN',
      })
    );
    return of(true);
  }

  public async hasRole(role: string): Promise<boolean> {
    try {
      let response = await this.userService.hasRole(
        this.authenticatedUser!.userId,
        role
      );
      return await firstValueFrom(response);
    } catch (error) {
      throwError(() => error);
    }
    return false;
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

  public async addUser(user: AppUser): Promise<AppUser | undefined> {
    try {
      let response = await this.userService.saveUser(user);
      let savedUser = await firstValueFrom(response);
      return savedUser;
    } catch (error) {
      throwError(() => error);
      return undefined;
    }
  }
}
