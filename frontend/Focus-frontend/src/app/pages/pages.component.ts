import { Component, OnInit } from '@angular/core';
import { AuthenticationService } from '../services/authentication/authentication.service';
import { UserService } from '../services/users/user.service';

@Component({
  selector: 'app-pages',
  templateUrl: './pages.component.html',
  styleUrls: ['./pages.component.scss'],
})
export class PagesComponent implements OnInit {
  constructor(private authService: AuthenticationService) {}

  ngOnInit(): void {}
  testingRefreshToken() {
    this.authService.validateAccessToken();
  }
}
