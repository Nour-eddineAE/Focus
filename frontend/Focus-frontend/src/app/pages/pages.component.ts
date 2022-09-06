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
}
