import { HttpErrorResponse } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { UUID } from 'angular2-uuid';
import { Observable, of, throwError } from 'rxjs';
import { AppUser } from '../../model/user.model';
import { UserService } from '../users/user.service';

@Injectable({
  providedIn: 'root',
})
export class AuthenticationService {
  users: AppUser[] = [];
  authenticatedUser: AppUser | undefined;
  errorMessage: string = '';

  constructor(private userService: UserService) {
    this.userService.getAllUsers().subscribe({
      next: (response: AppUser[]) => {
        this.users = response;
      },
      error: (error: HttpErrorResponse) => {
        this.errorMessage = error.message;
      },
    });
  }

  public login(usernameOrEmail: string, password: string): Observable<AppUser> {
    /*
      -tried getting data directly from the backend instead of fetching everything onInit(which i think is not a good idea)
      but that dosn't seem to work since return of http request is an observable, and takes a bit more of time,
      and since i need to do more tests on the data returned (as you see below), the code keeps executing 
      without having the data yet.
      i'll leave it like this until i find a better way to do it
    */

    let appUser = this.users.find(
      (user) =>
        user.username === usernameOrEmail || user.email === usernameOrEmail
    );

    if (appUser == undefined) {
      return throwError(() => new Error('User not found'));
    }
    if (appUser.password != password) {
      return throwError(() => new Error('Bad credentials'));
    }
    return of(appUser);
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

  public hasRole(role: string): boolean {
    return this.authenticatedUser!.roles.includes(role);
    /* i would want to use th backend mthd for security reasons, but for the same reason, as long as
     observables are lazy, the return will always be false
    
      |   this.userService.hasRole(this.authenticatedUser!.userId, role).subscribe({
      |    next: (response: boolean) => {
      |      return response;
      |    },
      |    error: (error: Error) => {
      |      this.errorMessage = error.message;
      |    },
      |  });
      |  return false;
    
    */
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

  public addUser(user: AppUser) {
    // save the user in the memory
    this.users.push(user);
    // then save him on the db
    this.userService.saveUser(user).subscribe({
      next: (data: AppUser) => {
        //some code that may need the saved user
        return;
      },
      error: (error: Error) => {
        // code that handles errors
        return;
      },
    });
  }
}
