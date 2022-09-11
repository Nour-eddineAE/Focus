import { Component, HostListener, OnInit } from '@angular/core';
import { FormBuilder, FormGroup } from '@angular/forms';
import { Router } from '@angular/router';
import { firstValueFrom } from 'rxjs';
import { LoginBody, Tokens } from 'src/app/model/user.model';
import { UserService } from 'src/app/services/users/user.service';
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
    private router: Router,
    private userService: UserService
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
    let loginBody: LoginBody = new LoginBody(username, password);
    let tokens: Tokens = {
      refreshToken: '',
      accessToken: '',
    };

    try {
      // try to authenticate
      let response = await this.authenticationService.login(loginBody);
      // if auth attempt provided undefined
      if (response == undefined) {
        this.errorMessage = 'Incorrect username or password';
        console.log(
          'ERROR INSIDE loginComponent.handleLogin, response undefined'
        );
      } else {
        tokens = await firstValueFrom(response);
        // then create session
        this.authenticationService.authenticate(tokens).subscribe({
          next: (data) => {
            this.router.navigateByUrl('/user');
          },
        });
      }
    } catch (error: any) {
      this.errorMessage = error.message;
      console.log('ERROR INSIDE loginComponent.handleLogin 2 ' + error.message);
    }
  }
  getClass(): string {
    return this.innerWidth < 845 ? 'form-container-md' : 'form-container';
  }
}
