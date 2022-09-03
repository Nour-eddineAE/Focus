import { HttpErrorResponse } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { UUID } from 'angular2-uuid';
import { AppUser } from '../model/user.model';
import { UserService } from '../services/users/user.service';

@Component({
  selector: 'app-pages',
  templateUrl: './pages.component.html',
  styleUrls: ['./pages.component.scss'],
})
export class PagesComponent implements OnInit {
  constructor(private userService: UserService) {}

  ngOnInit(): void {}

  //TODO: these mthds are temporary, made just for testing, remove them later
  hasRole() {
    this.userService
      .hasRole('eb4a8b04-194e-49fd-a2a3-69e7f6da0aeb', 'ADMIN')
      .subscribe({
        next: (response: boolean) => {
          console.log(response);
        },
        error: (error: HttpErrorResponse) => {
          console.log(error.message);
        },
      });
  }

  saveUser() {
    let user: AppUser = new AppUser();
    user.userId = UUID.UUID();
    user.email = 'aaaaaabbbbb';
    user.firstName = 'aaaaa';
    user.lastName = 'aaaaaa';
    user.username = 'aaaaaa';
    user.password = 'aaaaaa';
    user.roles = ['ADMIN'];
    this.userService.saveUser(user).subscribe({
      next: (response: AppUser) => {
        console.log(response);
      },
      error: (error: HttpErrorResponse) => {
        console.log(error.message);
      },
    });
  }

  getAllUsers() {
    this.userService.getAllUsers().subscribe({
      next: (response: AppUser[]) => {
        console.log(response);
      },
      error: (error: HttpErrorResponse) => {
        console.log(error.message);
      },
    });
  }

  getUserById() {
    let testingId = 'd57d7b67-ed41-4c6e-a5b4-419d96ddca26';
    this.userService.getUserById(testingId).subscribe({
      next: (response: AppUser) => {
        console.log(response);
      },
      error: (error: HttpErrorResponse) => {
        console.log(error.message);
      },
    });
  }
  getUserByUsername() {
    let testingUsername = 'masha masha';
    this.userService.getUserByUsername(testingUsername).subscribe({
      next: (response: AppUser) => {
        console.log(response);
      },
      error: (error: HttpErrorResponse) => {
        console.log(error.message);
      },
    });
  }
  getUserByEmail() {
    let testingEmail = 'email';
    let testingId = 'd57d7b67-ed41-4c6e-a5b4-419d96ddca26';
    this.userService.getUserByEmail(testingEmail).subscribe({
      next: (response: AppUser) => {
        console.log(response);
      },
      error: (error: HttpErrorResponse) => {
        console.log(error.message);
      },
    });
  }
}
