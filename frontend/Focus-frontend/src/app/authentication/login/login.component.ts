import { Component, HostListener, OnInit } from '@angular/core';
import { FormBuilder, FormGroup } from '@angular/forms';
import { Router } from '@angular/router';
import { firstValueFrom } from 'rxjs';
import { AuthenticationService } from '../../services/authentication/authentication.service';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.scss'],
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

    try {
      let response = await this.authenticationService.login(username, password);
      let appUser = await firstValueFrom(response);
      this.authenticationService.authenticate(appUser).subscribe({
        next: (data) => {
          this.router.navigateByUrl('/user');
        },
      });
    } catch (error: any) {
      this.errorMessage = error.message;
    }
  }

  getClass(): string {
    return this.innerWidth < 845 ? 'form-container-md' : 'form-container';
  }
}
