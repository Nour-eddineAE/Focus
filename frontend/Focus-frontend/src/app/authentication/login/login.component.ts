import { Component, HostListener, OnInit } from '@angular/core';
import { FormBuilder, FormGroup } from '@angular/forms';
import { Router } from '@angular/router';
import { AuthenticationService } from '../../services/authentication/authentication.service';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['../authentication.scss'],
})
export class LoginComponent implements OnInit {
  authenticationFormGroup!: FormGroup;
  errorMessage: any;
  innerWidth: number = window.innerWidth;
  hideError: boolean = false;
  constructor(
    private formBuilder: FormBuilder,
    private authenticationService: AuthenticationService,
    private router: Router
  ) {}

  ngOnInit(): void {
    this.authenticationFormGroup = this.formBuilder.group({
      username: this.formBuilder.control(''),
      password: this.formBuilder.control(''),
    });
  }
  @HostListener('window:resize', ['$event'])
  onResize(event: any) {
    this.innerWidth = window.innerWidth;
  }

  async handleLogin() {
    let username = this.authenticationFormGroup.value.username;
    let password = this.authenticationFormGroup.value.password;

    (await this.authenticationService.login(username, password)).subscribe({
      next: (appUser) => {
        this.authenticationService.authenticate(appUser).subscribe({
          next: (data) => {
            this.router.navigateByUrl('/user');
          },
        });
      },
      error: (error: Error) => {
        this.errorMessage = error.message;
      },
    });
  }

  getClass(): string {
    return this.innerWidth < 845 ? 'form-container-md' : 'form-container';
  }
}
