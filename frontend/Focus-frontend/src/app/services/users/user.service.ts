import { JsonPipe } from '@angular/common';
import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable, take } from 'rxjs';
import { AppUser } from 'src/app/model/user.model';

@Injectable({
  providedIn: 'root',
})
export class UserService {
  getUserByUsernameUrl: string = '';
  getUserByIdUrl: string = '';
  getUserByEmailUrl: string = '';
  getAllUsersUrl: string = '';
  hasRoleUrl: string = '';
  saveUserUrl: string = '';
  proxy: string = 'http://localhost:8081';
  constructor(private httpClient: HttpClient) {
    this.getUserByUsernameUrl = this.proxy + '/api/user/findByUsername/';

    this.getUserByIdUrl = this.proxy + '/api/user/findById/';

    this.getUserByEmailUrl = this.proxy + '/api/user/findByEmail/';

    this.getAllUsersUrl = this.proxy + '/api/user/allUsers';

    this.hasRoleUrl = this.proxy + '/api/user/hasRole/';

    this.saveUserUrl = this.proxy + '/api/user/addUser/';
  }

  getUserByUsername(username: string): Observable<AppUser> {
    return this.httpClient.get<AppUser>(this.getUserByUsernameUrl + username);
  }

  getUserByEmail(email: string): Observable<AppUser> {
    return this.httpClient.get<AppUser>(this.getUserByEmailUrl + email);
  }

  getUserById(userId: string): Observable<AppUser> {
    return this.httpClient.get<AppUser>(this.getUserByIdUrl + userId);
  }

  hasRole(userId: string, role: String): Observable<boolean> {
    return this.httpClient.get<boolean>(this.hasRoleUrl + userId + '/' + role);
  }

  getAllUsers(): Observable<AppUser[]> {
    return this.httpClient.get<AppUser[]>(this.getAllUsersUrl);
  }

  saveUser(user: AppUser): Observable<AppUser> {
    return this.httpClient.post<AppUser>(this.saveUserUrl, user);
  }
}
